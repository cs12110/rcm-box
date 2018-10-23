package com.rcm.cpu;

import java.util.Map;
import java.util.Set;

/**
 * 余弦相似度
 * 
 *
 * <p>
 *
 * @author cs12110 2018年10月22日
 * @see
 * @since 1.0
 */
public class CosineSimilarity {

	/**
	 * 计算余弦相似度,值在[-1,1]之间,越接近1越相似,越接近-1越不相似
	 * 
	 * <pre>
	 * 	cos = (x1*y1)+(x2*y2)+(x3*y3)/(sqrt((x1^2)+(x2^2)+(x3^2)*sqrt((y1^2)+(y2^2)+(y3^2))
	 * </pre>
	 * 
	 * @param value1
	 *            值1
	 * @param value2
	 *            值2
	 * @return double
	 */
	public static double compute(Map<String, Integer> value1, Map<String, Integer> value2) {
		if (null == value1 || null == value2) {
			return 0;
		}

		float sumXY = 0;
		float sumX2 = 0;
		float sumY2 = 0;

		Set<String> keySet = value1.size() > value1.size() ? value1.keySet() : value2.keySet();
		for (String key : keySet) {
			Integer tmp = value1.get(key);
			int a = tmp == null ? 0 : tmp;

			tmp = value2.get(key);
			int b = null == tmp ? 0 : tmp;

			sumXY += a * b;
			sumX2 += a * a;
			sumY2 += b * b;
		}
		double body = (Math.sqrt(sumX2) * Math.sqrt(sumY2));
		return 0 == body ? 0 : (sumXY / body);
	}
}
