# 基于相似度的推荐

在电影推荐里面,可以根据电影的类型做一个简单的相似度的推荐,找出和当前某一部电影最相似的几部电影推荐出去.

当然在现实里面,还要根据用户的购买里面去除推荐的数据.如果没有推荐的数据,还要考虑使用冷启动内容(比如最新上映的啦).

余弦相似度概念:[link](https://blog.csdn.net/rachel715/article/details/51700931)

## 1. 表结构

需要用到三张表, 电影表:`movie_t`,电影类型表:`type_t`,和 ta 们之间的关联表:`map_movie_type`.

### 1.1 电影表

```sql
DROP TABLE IF EXISTS `movie_t`;
CREATE TABLE `movie_t` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `director` varchar(255) DEFAULT NULL,
  `actors` varchar(512) DEFAULT NULL,
  `info` varchar(512) DEFAULT NULL,
  `op_time` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### 1.2 类型表

```sql
DROP TABLE IF EXISTS `type_t`;
CREATE TABLE `type_t` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### 1.3 关联表

```sql
DROP TABLE IF EXISTS `map_movie_type`;
CREATE TABLE `map_movie_type` (
  `movie_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

---

## 2. 关键代码

### 2.1 余弦相似度计算

```java
import java.util.Map;
import java.util.Set;

/**
 * 余弦相似度
 *
 *
 * <p>
 *
 * @author hhp 2018年10月22日
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
```

### 2.2 构建矩阵

Q: 要怎么余弦相似度的计算值?

A: 每一部电影里面对应着所有的类型,如果自己有的类型赋值为 1,否则该类型的值为 0.

这一步很重要,构建运算矩阵,直接关系到计算的结果.

---

## 3. 问题与思考

主要是在计算能力上面,通过上面的例子可以看出来当数据越大的时候(复杂度都是 n^2),运算时间越久.如果数据量很大的话,这个运算应该会撑爆内存弄崩服务器的.

所以这个只是最简单的余弦相似度的计算了,请知悉.
