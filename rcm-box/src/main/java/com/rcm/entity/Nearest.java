package com.rcm.entity;

/**
 * 相似度实体类
 * 
 *
 * <p>
 *
 * @author hhp 2018年10月22日
 * @see
 * @since 1.0
 */
public class Nearest {

	/**
	 * 电影Id
	 */
	private String movieId;

	/**
	 * 相似度值
	 */
	private double similar;

	public Nearest(String vodId, double score) {
		super();
		this.movieId = vodId;
		this.similar = score;
	}

	public String getVodId() {
		return movieId;
	}

	public void setVodId(String vodId) {
		this.movieId = vodId;
	}

	public double getScore() {
		return similar;
	}

	public void setScore(double score) {
		this.similar = score;
	}

	@Override
	public String toString() {
		return "Nearest [vodId=" + movieId + ", score=" + similar + "]";
	}

}
