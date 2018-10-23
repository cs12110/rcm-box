package com.rcm.util;

import com.rcm.entity.Nearest;

/**
 * 打印数据
 * 
 *
 * <p>
 *
 * @author hhp 2018年10月22日
 * @see
 * @since 1.0
 */
public class StdUtil {

	/**
	 * 打印推荐内容
	 * 
	 * @param movieId
	 *            电影Id
	 * @param arr
	 *            {@link Nearest} 数组
	 */
	public static void display(String movieId, Nearest[] arr) {
		StringBuilder body = new StringBuilder();
		for (int index = 0, len = arr.length; index < len; index++) {
			Nearest n = arr[index];
			if (null != n) {
				body.append(n.getVodId());
				body.append(":");
				String scoreStr = String.valueOf(n.getScore());
				int buond = scoreStr.length() > 4 ? 4 : scoreStr.length();
				body.append(scoreStr.substring(0, buond));
				if (index < len - 1) {
					body.append(" , ");
				}
			}
		}
		System.out.println(movieId + " -> " + body);
	}

}
