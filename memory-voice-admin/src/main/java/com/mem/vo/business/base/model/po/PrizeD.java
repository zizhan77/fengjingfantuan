package com.mem.vo.business.base.model.po;import com.mem.vo.business.base.model.vo.EticketVO;import com.mem.vo.business.base.model.vo.IntegralVO;import lombok.AllArgsConstructor;import lombok.Builder;import lombok.Data;import lombok.NoArgsConstructor;import java.util.Date;import java.util.List;import java.math.BigDecimal;/** *  * <br> * <b>功能：</b>PrizeD实体类<br> */@Builder@NoArgsConstructor@AllArgsConstructor@Datapublic class PrizeD{	/**	 *	 */	private Integer id;	/**	 *	 */	private Integer prizeId;	/**	 * 奖品类型	 */	private Integer prizeType;	/**	 * 积分数量	 */	private Integer integralQty;	/**	 * 概率	 */	private BigDecimal prob;	/**	 * 电子票码	 */	private String eticketCode;	/**	 * 具体奖品名称，如：白跃辉演唱会门票	 */	private String name;	/**	 * 是否兑换	 */	private Integer isChange;	/**	 *	 */	private Date createTime;	/**	 *	 */	private String createUser;	/**	 *	 */	private Date updateTime;	/**	 *	 */	private String updateUser;	/**	 * 删除标识  0 不删除 1 删除	 */	private Integer isDelete;	/**	 *	 */	private Date ts;	/**	 * 活动id	 */	private Long activityId;	/**	 * 奖品说明	 */	private String prizeIntro;	/**	 * 对应prize表奖品名称,如：门票，积分，实物奖品	 */	private String prizeName;	/**	 *奖品数量，数据库无此字段，处理使用。增加奖品时，填写的明细数量	 */	private Integer prizeNum;	/**	 * 奖品明细列表	 */	private List<IntegralVO> integralProbList;	/**	 *电子票列表	 */	private List<EticketVO> eticketList;}