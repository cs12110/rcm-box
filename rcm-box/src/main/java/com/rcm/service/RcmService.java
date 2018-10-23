package com.rcm.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.rcm.cpu.CosineSimilarity;
import com.rcm.entity.Nearest;
import com.rcm.util.JdbcUtil;

/**
 * 推荐服务类
 * 
 *
 * <p>
 *
 * @author hhp 2018年10月22日
 * @see
 * @since 1.0
 */
public class RcmService {

	/**
	 * 获取相似度最高的内容
	 * 
	 * @param movieId
	 *            电影Id
	 * @return {@link Nearest}
	 */
	public static Nearest[] getRcm(String movieId) {
		Map<String, Map<String, Integer>> datas = buildMatrix();

		return calculateRcmValue(datas, movieId);
	}

	/**
	 * 组建稀松矩阵
	 * 
	 * <pre>
	 * movieId:{
	 *		{typeId1:1,typeId2:0}
	 * }
	 * </pre>
	 * 
	 * @param typeMap
	 *            类型
	 * @return String
	 */
	private static Map<String, Map<String, Integer>> buildMatrix() {
		Map<String, Map<String, Integer>> map = new HashMap<>();
		Map<String, String> typeMap = new HashMap<>();
		try {
			String selectMovieSql = "SELECT movie_t.id,type_id FROM movie_t LEFT JOIN map_movie_type ON map_movie_type.movie_id = movie_t.id";
			String selectTypeSql = "SELECT id, name FROM type_t";

			Connection conn = JdbcUtil.getConnection();
			Statement stm = conn.createStatement();

			ResultSet result = stm.executeQuery(selectTypeSql);
			while (result.next()) {
				String vodId = result.getString("id");
				String name = result.getString("name");
				typeMap.put(vodId, name);
			}

			result = stm.executeQuery(selectMovieSql);
			while (result.next()) {
				String vodId = result.getString("id");
				String typeId = result.getString("type_id");
				if (null != typeId) {
					Map<String, Integer> valueMap = map.get(vodId);
					if (valueMap == null) {
						valueMap = new HashMap<>();
					}
					valueMap.put(typeId, 1);
					map.put(vodId, valueMap);
				}
			}
			result.close();
			stm.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Map.Entry<String, Map<String, Integer>> each : map.entrySet()) {
			Map<String, Integer> typeValues = each.getValue();
			for (String key : typeMap.keySet()) {
				if (typeValues.get(key) == null) {
					typeValues.put(key, 0);
				}
			}
			map.put(each.getKey(), typeValues);
		}
		return map;
	}

	/**
	 * 计算推荐内容
	 * 
	 * @param datas
	 *            数据矩阵
	 * @param movieId
	 *            电影Id
	 * @return Map
	 */
	private static Nearest[] calculateRcmValue(Map<String, Map<String, Integer>> datas, String movieId) {
		Nearest[] nearestArr = new Nearest[5];
		Map<String, Integer> value1 = datas.get(movieId);
		for (Map.Entry<String, Map<String, Integer>> each : datas.entrySet()) {
			if (movieId.equals(each.getKey())) {
				continue;
			}
			Map<String, Integer> value2 = each.getValue();
			double value = CosineSimilarity.compute(value1, value2);

			if (value > 0) {
				Nearest current = new Nearest(each.getKey(), value);
				fixNearest(nearestArr, current);
			}
		}

		return nearestArr;
	}

	/**
	 * 获取相似度最接近的5个电影
	 * 
	 * @param nearestArr
	 *            相似度数组
	 * @param append
	 *            追加元素
	 */
	private static void fixNearest(Nearest[] nearestArr, Nearest append) {
		int index = 0;
		int len = nearestArr.length;
		for (; index < len; index++) {
			Nearest tmp = nearestArr[index];
			if (tmp == null || tmp.getScore() < append.getScore()) {
				break;
			}
		}
		for (int t = len - 1; t > index; t--) {
			nearestArr[t] = nearestArr[t - 1];
		}
		if (index < len) {
			nearestArr[index] = append;
		}
	}

}
