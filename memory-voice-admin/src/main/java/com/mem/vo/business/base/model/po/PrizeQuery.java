package com.mem.vo.business.base.model.po;import lombok.AllArgsConstructor;import lombok.Builder;import lombok.Data;import lombok.NoArgsConstructor;import java.math.BigDecimal;import java.util.Date;/** * <br> * <b>功能：</b>Prize实体类<br> */@Builder@NoArgsConstructor@AllArgsConstructor@Datapublic class PrizeQuery {    /**     *     */    private Integer id;    /**     * 奖品名称     */    private String prizeName;    /**     * 活动id     */    private Long activityId;    /**     * 奖品概率     */    private BigDecimal prob;    /**     * 奖品总数     */    private Integer totalNum;    /**     * 已发放奖品数量     */    private Integer givedNum;    /**     * 显示奖品数量     */    private Integer showNum;    /**     * 奖品类型 电子票-1 实物-2 积分-3     */    private Integer prizeType;    /**     * 奖品说明     */    private String prizeIntro;    /**     * 奖品规则     */    private String prizeRule;    /**     *     */    private Date createTime;    /**     *     */    private String createUser;    /**     *     */    private Date updateTime;    /**     *     */    private String updateUser;    /**     * 删除标识  0 有效 1 无效     */    private Integer isDelete;    /**     *     */    private Date ts;    /**     * 赞助商ID     */    private Integer sponsorId;    private Integer status;    private Integer level;    private String count;    private Integer miniType;    private Integer codeType;    private Integer dailyTicketLimit;}