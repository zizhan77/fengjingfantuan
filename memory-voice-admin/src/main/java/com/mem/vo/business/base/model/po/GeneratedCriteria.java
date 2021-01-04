package com.mem.vo.business.base.model.po;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class GeneratedCriteria {
    protected List<Criterion> criteria = new ArrayList<>();

    public boolean isValid() {
        return (this.criteria.size() > 0);
    }

    public List<Criterion> getAllCriteria() {
        return this.criteria;
    }

    public List<Criterion> getCriteria() {
        return this.criteria;
    }

    protected void addCriterion(String condition) {
        if (condition == null) {
            throw new RuntimeException("Value for condition cannot be null");
        }
        this.criteria.add(new Criterion(condition));
    }

    protected void addCriterion(String condition, Object value, String property) {
        if (value == null) {
            throw new RuntimeException("Value for " + property + " cannot be null");
        }
        this.criteria.add(new Criterion(condition, value));
    }

    protected void addCriterion(String condition, Object value1, Object value2, String property) {
        if (value1 == null || value2 == null) {
            throw new RuntimeException("Between values for " + property + " cannot be null");
        }
        this.criteria.add(new Criterion(condition, value1, value2));
    }

    public Criteria andIdIsNull() {
        addCriterion("id is null");
        return (Criteria)this;
    }

    public Criteria andIdIsNotNull() {
        addCriterion("id is not null");
        return (Criteria)this;
    }

    public Criteria andIdEqualTo(Integer value) {
        addCriterion("id =", value, "id");
        return (Criteria) this;
    }

    public Criteria andIdNotEqualTo(Integer value) {
        addCriterion("id <>", value, "id");
        return (Criteria)this;
    }

    public Criteria andIdGreaterThan(Integer value) {
        addCriterion("id >", value, "id");
        return (Criteria)this;
    }

    public Criteria andIdGreaterThanOrEqualTo(Integer value) {
        addCriterion("id >=", value, "id");
        return (Criteria)this;
    }

    public Criteria andIdLessThan(Integer value) {
        addCriterion("id <", value, "id");
        return (Criteria)this;
    }

    public Criteria andIdLessThanOrEqualTo(Integer value) {
        addCriterion("id <=", value, "id");
        return (Criteria)this;
    }

    public Criteria andIdIn(List<Integer> values) {
        addCriterion("id in", values, "id");
        return (Criteria)this;
    }

    public Criteria andIdNotIn(List<Integer> values) {
        addCriterion("id not in", values, "id");
        return (Criteria)this;
    }

    public Criteria andIdBetween(Integer value1, Integer value2) {
        addCriterion("id between", value1, value2, "id");
        return (Criteria)this;
    }

    public Criteria andIdNotBetween(Integer value1, Integer value2) {
        addCriterion("id not between", value1, value2, "id");
        return (Criteria)this;
    }

    public Criteria andSponsoridIsNull() {
        addCriterion("sponsorId is null");
        return (Criteria)this;
    }

    public Criteria andSponsoridIsNotNull() {
        addCriterion("sponsorId is not null");
        return (Criteria)this;
    }

    public Criteria andSponsoridEqualTo(Integer value) {
        addCriterion("sponsorId =", value, "sponsorid");
        return (Criteria)this;
    }

    public Criteria andSponsoridNotEqualTo(Integer value) {
        addCriterion("sponsorId <>", value, "sponsorid");
        return (Criteria)this;
    }

    public Criteria andSponsoridGreaterThan(Integer value) {
        addCriterion("sponsorId >", value, "sponsorid");
        return (Criteria)this;
    }

    public Criteria andSponsoridGreaterThanOrEqualTo(Integer value) {
        addCriterion("sponsorId >=", value, "sponsorid");
        return (Criteria)this;
    }

    public Criteria andSponsoridLessThan(Integer value) {
        addCriterion("sponsorId <", value, "sponsorid");
        return (Criteria)this;
    }

    public Criteria andSponsoridLessThanOrEqualTo(Integer value) {
        addCriterion("sponsorId <=", value, "sponsorid");
        return (Criteria)this;
    }

    public Criteria andSponsoridIn(List<Integer> values) {
        addCriterion("sponsorId in", values, "sponsorid");
        return (Criteria)this;
    }

    public Criteria andSponsoridNotIn(List<Integer> values) {
        addCriterion("sponsorId not in", values, "sponsorid");
        return (Criteria)this;
    }

    public Criteria andSponsoridBetween(Integer value1, Integer value2) {
        addCriterion("sponsorId between", value1, value2, "sponsorid");
        return (Criteria)this;
    }

    public Criteria andSponsoridNotBetween(Integer value1, Integer value2) {
        addCriterion("sponsorId not between", value1, value2, "sponsorid");
        return (Criteria)this;
    }

    public Criteria andContentIsNull() {
        addCriterion("content is null");
        return (Criteria)this;
    }

    public Criteria andContentIsNotNull() {
        addCriterion("content is not null");
        return (Criteria)this;
    }

    public Criteria andContentEqualTo(String value) {
        addCriterion("content =", value, "content");
        return (Criteria)this;
    }

    public Criteria andContentNotEqualTo(String value) {
        addCriterion("content <>", value, "content");
        return (Criteria)this;
    }

    public Criteria andContentGreaterThan(String value) {
        addCriterion("content >", value, "content");
        return (Criteria)this;
    }

    public Criteria andContentGreaterThanOrEqualTo(String value) {
        addCriterion("content >=", value, "content");
        return (Criteria)this;
    }

    public Criteria andContentLessThan(String value) {
        addCriterion("content <", value, "content");
        return (Criteria)this;
    }

    public Criteria andContentLessThanOrEqualTo(String value) {
        addCriterion("content <=", value, "content");
        return (Criteria)this;
    }

    public Criteria andContentLike(String value) {
        addCriterion("content like", value, "content");
        return (Criteria)this;
    }

    public Criteria andContentNotLike(String value) {
        addCriterion("content not like", value, "content");
        return (Criteria)this;
    }

    public Criteria andContentIn(List<String> values) {
        addCriterion("content in", values, "content");
        return (Criteria)this;
    }

    public Criteria andContentNotIn(List<String> values) {
        addCriterion("content not in", values, "content");
        return (Criteria)this;
    }

    public Criteria andContentBetween(String value1, String value2) {
        addCriterion("content between", value1, value2, "content");
        return (Criteria)this;
    }

    public Criteria andContentNotBetween(String value1, String value2) {
        addCriterion("content not between", value1, value2, "content");
        return (Criteria)this;
    }

    public Criteria andShowTimeIsNull() {
        addCriterion("show_time is null");
        return (Criteria)this;
    }

    public Criteria andShowTimeIsNotNull() {
        addCriterion("show_time is not null");
        return (Criteria)this;
    }

    public Criteria andShowTimeEqualTo(Date value) {
        addCriterion("show_time =", value, "showTime");
        return (Criteria)this;
    }

    public Criteria andShowTimeNotEqualTo(Date value) {
        addCriterion("show_time <>", value, "showTime");
        return (Criteria)this;
    }

    public Criteria andShowTimeGreaterThan(Date value) {
        addCriterion("show_time >", value, "showTime");
        return (Criteria)this;
    }

    public Criteria andShowTimeGreaterThanOrEqualTo(Date value) {
        addCriterion("show_time >=", value, "showTime");
        return (Criteria)this;
    }

    public Criteria andShowTimeLessThan(Date value) {
        addCriterion("show_time <", value, "showTime");
        return (Criteria)this;
    }

    public Criteria andShowTimeLessThanOrEqualTo(Date value) {
        addCriterion("show_time <=", value, "showTime");
        return (Criteria)this;
    }

    public Criteria andShowTimeIn(List<Date> values) {
        addCriterion("show_time in", values, "showTime");
        return (Criteria)this;
    }

    public Criteria andShowTimeNotIn(List<Date> values) {
        addCriterion("show_time not in", values, "showTime");
        return (Criteria)this;
    }

    public Criteria andShowTimeBetween(Date value1, Date value2) {
        addCriterion("show_time between", value1, value2, "showTime");
        return (Criteria)this;
    }

    public Criteria andShowTimeNotBetween(Date value1, Date value2) {
        addCriterion("show_time not between", value1, value2, "showTime");
        return (Criteria)this;
    }

    public Criteria andArtistidIsNull() {
        addCriterion("artistId is null");
        return (Criteria)this;
    }

    public Criteria andArtistidIsNotNull() {
        addCriterion("artistId is not null");
        return (Criteria)this;
    }

    public Criteria andArtistidEqualTo(Integer value) {
        addCriterion("artistId =", value, "artistid");
        return (Criteria)this;
    }

    public Criteria andArtistidNotEqualTo(Integer value) {
        addCriterion("artistId <>", value, "artistid");
        return (Criteria)this;
    }

    public Criteria andArtistidGreaterThan(Integer value) {
        addCriterion("artistId >", value, "artistid");
        return (Criteria)this;
    }

    public Criteria andArtistidGreaterThanOrEqualTo(Integer value) {
        addCriterion("artistId >=", value, "artistid");
        return (Criteria)this;
    }

    public Criteria andArtistidLessThan(Integer value) {
        addCriterion("artistId <", value, "artistid");
        return (Criteria)this;
    }

    public Criteria andArtistidLessThanOrEqualTo(Integer value) {
        addCriterion("artistId <=", value, "artistid");
        return (Criteria)this;
    }

    public Criteria andArtistidIn(List<Integer> values) {
        addCriterion("artistId in", values, "artistid");
        return (Criteria)this;
    }

    public Criteria andArtistidNotIn(List<Integer> values) {
        addCriterion("artistId not in", values, "artistid");
        return (Criteria)this;
    }

    public Criteria andArtistidBetween(Integer value1, Integer value2) {
        addCriterion("artistId between", value1, value2, "artistid");
        return (Criteria)this;
    }

    public Criteria andArtistidNotBetween(Integer value1, Integer value2) {
        addCriterion("artistId not between", value1, value2, "artistid");
        return (Criteria)this;
    }

    public Criteria andCityidIsNull() {
        addCriterion("cityId is null");
        return (Criteria)this;
    }

    public Criteria andCityidIsNotNull() {
        addCriterion("cityId is not null");
        return (Criteria)this;
    }

    public Criteria andCityidEqualTo(Integer value) {
        addCriterion("cityId =", value, "cityid");
        return (Criteria)this;
    }

    public Criteria andCityidNotEqualTo(Integer value) {
        addCriterion("cityId <>", value, "cityid");
        return (Criteria)this;
    }

    public Criteria andCityidGreaterThan(Integer value) {
        addCriterion("cityId >", value, "cityid");
        return (Criteria)this;
    }

    public Criteria andCityidGreaterThanOrEqualTo(Integer value) {
        addCriterion("cityId >=", value, "cityid");
        return (Criteria)this;
    }

    public Criteria andCityidLessThan(Integer value) {
        addCriterion("cityId <", value, "cityid");
        return (Criteria)this;
    }

    public Criteria andCityidLessThanOrEqualTo(Integer value) {
        addCriterion("cityId <=", value, "cityid");
        return (Criteria)this;
    }

    public Criteria andCityidIn(List<Integer> values) {
        addCriterion("cityId in", values, "cityid");
        return (Criteria)this;
    }

    public Criteria andCityidNotIn(List<Integer> values) {
        addCriterion("cityId not in", values, "cityid");
        return (Criteria)this;
    }

    public Criteria andCityidBetween(Integer value1, Integer value2) {
        addCriterion("cityId between", value1, value2, "cityid");
        return (Criteria)this;
    }

    public Criteria andCityidNotBetween(Integer value1, Integer value2) {
        addCriterion("cityId not between", value1, value2, "cityid");
        return (Criteria)this;
    }

    public Criteria andPlaceidIsNull() {
        addCriterion("placeId is null");
        return (Criteria)this;
    }

    public Criteria andPlaceidIsNotNull() {
        addCriterion("placeId is not null");
        return (Criteria)this;
    }

    public Criteria andPlaceidEqualTo(Integer value) {
        addCriterion("placeId =", value, "placeid");
        return (Criteria)this;
    }

    public Criteria andPlaceidNotEqualTo(Integer value) {
        addCriterion("placeId <>", value, "placeid");
        return (Criteria)this;
    }

    public Criteria andPlaceidGreaterThan(Integer value) {
        addCriterion("placeId >", value, "placeid");
        return (Criteria)this;
    }

    public Criteria andPlaceidGreaterThanOrEqualTo(Integer value) {
        addCriterion("placeId >=", value, "placeid");
        return (Criteria)this;
    }

    public Criteria andPlaceidLessThan(Integer value) {
        addCriterion("placeId <", value, "placeid");
        return (Criteria)this;
    }

    public Criteria andPlaceidLessThanOrEqualTo(Integer value) {
        addCriterion("placeId <=", value, "placeid");
        return (Criteria)this;
    }

    public Criteria andPlaceidIn(List<Integer> values) {
        addCriterion("placeId in", values, "placeid");
        return (Criteria)this;
    }

    public Criteria andPlaceidNotIn(List<Integer> values) {
        addCriterion("placeId not in", values, "placeid");
        return (Criteria)this;
    }

    public Criteria andPlaceidBetween(Integer value1, Integer value2) {
        addCriterion("placeId between", value1, value2, "placeid");
        return (Criteria)this;
    }

    public Criteria andPlaceidNotBetween(Integer value1, Integer value2) {
        addCriterion("placeId not between", value1, value2, "placeid");
        return (Criteria)this;
    }

    public Criteria andPriceIsNull() {
        addCriterion("price is null");
        return (Criteria)this;
    }

    public Criteria andPriceIsNotNull() {
        addCriterion("price is not null");
        return (Criteria)this;
    }

    public Criteria andPriceEqualTo(BigDecimal value) {
        addCriterion("price =", value, "price");
        return (Criteria)this;
    }

    public Criteria andPriceNotEqualTo(BigDecimal value) {
        addCriterion("price <>", value, "price");
        return (Criteria)this;
    }

    public Criteria andPriceGreaterThan(BigDecimal value) {
        addCriterion("price >", value, "price");
        return (Criteria)this;
    }

    public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
        addCriterion("price >=", value, "price");
        return (Criteria)this;
    }

    public Criteria andPriceLessThan(BigDecimal value) {
        addCriterion("price <", value, "price");
        return (Criteria)this;
    }

    public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
        addCriterion("price <=", value, "price");
        return (Criteria)this;
    }

    public Criteria andPriceIn(List<BigDecimal> values) {
        addCriterion("price in", values, "price");
        return (Criteria)this;
    }

    public Criteria andPriceNotIn(List<BigDecimal> values) {
        addCriterion("price not in", values, "price");
        return (Criteria)this;
    }

    public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
        addCriterion("price between", value1, value2, "price");
        return (Criteria)this;
    }

    public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
        addCriterion("price not between", value1, value2, "price");
        return (Criteria)this;
    }

    public Criteria andOrganizeridIsNull() {
        addCriterion("organizerId is null");
        return (Criteria)this;
    }

    public Criteria andOrganizeridIsNotNull() {
        addCriterion("organizerId is not null");
        return (Criteria)this;
    }

    public Criteria andOrganizeridEqualTo(Integer value) {
        addCriterion("organizerId =", value, "organizerid");
        return (Criteria)this;
    }

    public Criteria andOrganizeridNotEqualTo(Integer value) {
        addCriterion("organizerId <>", value, "organizerid");
        return (Criteria)this;
    }

    public Criteria andOrganizeridGreaterThan(Integer value) {
        addCriterion("organizerId >", value, "organizerid");
        return (Criteria)this;
    }

    public Criteria andOrganizeridGreaterThanOrEqualTo(Integer value) {
        addCriterion("organizerId >=", value, "organizerid");
        return (Criteria)this;
    }

    public Criteria andOrganizeridLessThan(Integer value) {
        addCriterion("organizerId <", value, "organizerid");
        return (Criteria)this;
    }

    public Criteria andOrganizeridLessThanOrEqualTo(Integer value) {
        addCriterion("organizerId <=", value, "organizerid");
        return (Criteria)this;
    }

    public Criteria andOrganizeridIn(List<Integer> values) {
        addCriterion("organizerId in", values, "organizerid");
        return (Criteria)this;
    }

    public Criteria andOrganizeridNotIn(List<Integer> values) {
        addCriterion("organizerId not in", values, "organizerid");
        return (Criteria)this;
    }

    public Criteria andOrganizeridBetween(Integer value1, Integer value2) {
        addCriterion("organizerId between", value1, value2, "organizerid");
        return (Criteria)this;
    }

    public Criteria andOrganizeridNotBetween(Integer value1, Integer value2) {
        addCriterion("organizerId not between", value1, value2, "organizerid");
        return (Criteria)this;
    }

    public Criteria andClassidIsNull() {
        addCriterion("classId is null");
        return (Criteria)this;
    }

    public Criteria andClassidIsNotNull() {
        addCriterion("classId is not null");
        return (Criteria)this;
    }

    public Criteria andClassidEqualTo(Integer value) {
        addCriterion("classId =", value, "classid");
        return (Criteria)this;
    }

    public Criteria andClassidNotEqualTo(Integer value) {
        addCriterion("classId <>", value, "classid");
        return (Criteria)this;
    }

    public Criteria andClassidGreaterThan(Integer value) {
        addCriterion("classId >", value, "classid");
        return (Criteria)this;
    }

    public Criteria andClassidGreaterThanOrEqualTo(Integer value) {
        addCriterion("classId >=", value, "classid");
        return (Criteria)this;
    }

    public Criteria andClassidLessThan(Integer value) {
        addCriterion("classId <", value, "classid");
        return (Criteria)this;
    }

    public Criteria andClassidLessThanOrEqualTo(Integer value) {
        addCriterion("classId <=", value, "classid");
        return (Criteria)this;
    }

    public Criteria andClassidIn(List<Integer> values) {
        addCriterion("classId in", values, "classid");
        return (Criteria)this;
    }

    public Criteria andClassidNotIn(List<Integer> values) {
        addCriterion("classId not in", values, "classid");
        return (Criteria)this;
    }

    public Criteria andClassidBetween(Integer value1, Integer value2) {
        addCriterion("classId between", value1, value2, "classid");
        return (Criteria)this;
    }

    public Criteria andClassidNotBetween(Integer value1, Integer value2) {
        addCriterion("classId not between", value1, value2, "classid");
        return (Criteria)this;
    }

    public Criteria andStopTimeIsNull() {
        addCriterion("stop_time is null");
        return (Criteria)this;
    }

    public Criteria andStopTimeIsNotNull() {
        addCriterion("stop_time is not null");
        return (Criteria)this;
    }

    public Criteria andStopTimeEqualTo(Date value) {
        addCriterion("stop_time =", value, "stopTime");
        return (Criteria)this;
    }

    public Criteria andStopTimeNotEqualTo(Date value) {
        addCriterion("stop_time <>", value, "stopTime");
        return (Criteria)this;
    }

    public Criteria andStopTimeGreaterThan(Date value) {
        addCriterion("stop_time >", value, "stopTime");
        return (Criteria)this;
    }

    public Criteria andStopTimeGreaterThanOrEqualTo(Date value) {
        addCriterion("stop_time >=", value, "stopTime");
        return (Criteria)this;
    }

    public Criteria andStopTimeLessThan(Date value) {
        addCriterion("stop_time <", value, "stopTime");
        return (Criteria)this;
    }

    public Criteria andStopTimeLessThanOrEqualTo(Date value) {
        addCriterion("stop_time <=", value, "stopTime");
        return (Criteria)this;
    }

    public Criteria andStopTimeIn(List<Date> values) {
        addCriterion("stop_time in", values, "stopTime");
        return (Criteria)this;
    }

    public Criteria andStopTimeNotIn(List<Date> values) {
        addCriterion("stop_time not in", values, "stopTime");
        return (Criteria)this;
    }

    public Criteria andStopTimeBetween(Date value1, Date value2) {
        addCriterion("stop_time between", value1, value2, "stopTime");
        return (Criteria)this;
    }

    public Criteria andStopTimeNotBetween(Date value1, Date value2) {
        addCriterion("stop_time not between", value1, value2, "stopTime");
        return (Criteria)this;
    }

    public Criteria andStatusIsNull() {
        addCriterion("status is null");
        return (Criteria)this;
    }

    public Criteria andStatusIsNotNull() {
        addCriterion("status is not null");
        return (Criteria)this;
    }

    public Criteria andStatusEqualTo(Byte value) {
        addCriterion("status =", value, "status");
        return (Criteria)this;
    }

    public Criteria andStatusNotEqualTo(Byte value) {
        addCriterion("status <>", value, "status");
        return (Criteria)this;
    }

    public Criteria andStatusGreaterThan(Byte value) {
        addCriterion("status >", value, "status");
        return (Criteria)this;
    }

    public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
        addCriterion("status >=", value, "status");
        return (Criteria)this;
    }

    public Criteria andStatusLessThan(Byte value) {
        addCriterion("status <", value, "status");
        return (Criteria)this;
    }

    public Criteria andStatusLessThanOrEqualTo(Byte value) {
        addCriterion("status <=", value, "status");
        return (Criteria)this;
    }

    public Criteria andStatusIn(List<Byte> values) {
        addCriterion("status in", values, "status");
        return (Criteria)this;
    }

    public Criteria andStatusNotIn(List<Byte> values) {
        addCriterion("status not in", values, "status");
        return (Criteria)this;
    }

    public Criteria andStatusBetween(Byte value1, Byte value2) {
        addCriterion("status between", value1, value2, "status");
        return (Criteria)this;
    }

    public Criteria andStatusNotBetween(Byte value1, Byte value2) {
        addCriterion("status not between", value1, value2, "status");
        return (Criteria)this;
    }

    public Criteria andCreateTimeIsNull() {
        addCriterion("create_time is null");
        return (Criteria)this;
    }

    public Criteria andCreateTimeIsNotNull() {
        addCriterion("create_time is not null");
        return (Criteria)this;
    }

    public Criteria andCreateTimeEqualTo(Date value) {
        addCriterion("create_time =", value, "createTime");
        return (Criteria)this;
    }

    public Criteria andCreateTimeNotEqualTo(Date value) {
        addCriterion("create_time <>", value, "createTime");
        return (Criteria)this;
    }

    public Criteria andCreateTimeGreaterThan(Date value) {
        addCriterion("create_time >", value, "createTime");
        return (Criteria)this;
    }

    public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
        addCriterion("create_time >=", value, "createTime");
        return (Criteria)this;
    }

    public Criteria andCreateTimeLessThan(Date value) {
        addCriterion("create_time <", value, "createTime");
        return (Criteria)this;
    }

    public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
        addCriterion("create_time <=", value, "createTime");
        return (Criteria)this;
    }

    public Criteria andCreateTimeIn(List<Date> values) {
        addCriterion("create_time in", values, "createTime");
        return (Criteria)this;
    }

    public Criteria andCreateTimeNotIn(List<Date> values) {
        addCriterion("create_time not in", values, "createTime");
        return (Criteria)this;
    }

    public Criteria andCreateTimeBetween(Date value1, Date value2) {
        addCriterion("create_time between", value1, value2, "createTime");
        return (Criteria)this;
    }

    public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
        addCriterion("create_time not between", value1, value2, "createTime");
        return (Criteria)this;
    }

    public Criteria andCreateUserIsNull() {
        addCriterion("create_user is null");
        return (Criteria)this;
    }

    public Criteria andCreateUserIsNotNull() {
        addCriterion("create_user is not null");
        return (Criteria)this;
    }

    public Criteria andCreateUserEqualTo(String value) {
        addCriterion("create_user =", value, "createUser");
        return (Criteria)this;
    }

    public Criteria andCreateUserNotEqualTo(String value) {
        addCriterion("create_user <>", value, "createUser");
        return (Criteria)this;
    }

    public Criteria andCreateUserGreaterThan(String value) {
        addCriterion("create_user >", value, "createUser");
        return (Criteria)this;
    }

    public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
        addCriterion("create_user >=", value, "createUser");
        return (Criteria)this;
    }

    public Criteria andCreateUserLessThan(String value) {
        addCriterion("create_user <", value, "createUser");
        return (Criteria)this;
    }

    public Criteria andCreateUserLessThanOrEqualTo(String value) {
        addCriterion("create_user <=", value, "createUser");
        return (Criteria)this;
    }

    public Criteria andCreateUserLike(String value) {
        addCriterion("create_user like", value, "createUser");
        return (Criteria)this;
    }

    public Criteria andCreateUserNotLike(String value) {
        addCriterion("create_user not like", value, "createUser");
        return (Criteria)this;
    }

    public Criteria andCreateUserIn(List<String> values) {
        addCriterion("create_user in", values, "createUser");
        return (Criteria)this;
    }

    public Criteria andCreateUserNotIn(List<String> values) {
        addCriterion("create_user not in", values, "createUser");
        return (Criteria)this;
    }

    public Criteria andCreateUserBetween(String value1, String value2) {
        addCriterion("create_user between", value1, value2, "createUser");
        return (Criteria)this;
    }

    public Criteria andCreateUserNotBetween(String value1, String value2) {
        addCriterion("create_user not between", value1, value2, "createUser");
        return (Criteria)this;
    }

    public Criteria andUpdateTimeIsNull() {
        addCriterion("update_time is null");
        return (Criteria)this;
    }

    public Criteria andUpdateTimeIsNotNull() {
        addCriterion("update_time is not null");
        return (Criteria)this;
    }

    public Criteria andUpdateTimeEqualTo(Date value) {
        addCriterion("update_time =", value, "updateTime");
        return (Criteria)this;
    }

    public Criteria andUpdateTimeNotEqualTo(Date value) {
        addCriterion("update_time <>", value, "updateTime");
        return (Criteria)this;
    }

    public Criteria andUpdateTimeGreaterThan(Date value) {
        addCriterion("update_time >", value, "updateTime");
        return (Criteria)this;
    }

    public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
        addCriterion("update_time >=", value, "updateTime");
        return (Criteria)this;
    }

    public Criteria andUpdateTimeLessThan(Date value) {
        addCriterion("update_time <", value, "updateTime");
        return (Criteria)this;
    }

    public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
        addCriterion("update_time <=", value, "updateTime");
        return (Criteria)this;
    }

    public Criteria andUpdateTimeIn(List<Date> values) {
        addCriterion("update_time in", values, "updateTime");
        return (Criteria)this;
    }

    public Criteria andUpdateTimeNotIn(List<Date> values) {
        addCriterion("update_time not in", values, "updateTime");
        return (Criteria)this;
    }

    public Criteria andUpdateTimeBetween(Date value1, Date value2) {
        addCriterion("update_time between", value1, value2, "updateTime");
        return (Criteria)this;
    }

    public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
        addCriterion("update_time not between", value1, value2, "updateTime");
        return (Criteria)this;
    }

    public Criteria andUpdateUserIsNull() {
        addCriterion("update_user is null");
        return (Criteria)this;
    }

    public Criteria andUpdateUserIsNotNull() {
        addCriterion("update_user is not null");
        return (Criteria)this;
    }

    public Criteria andUpdateUserEqualTo(String value) {
        addCriterion("update_user =", value, "updateUser");
        return (Criteria)this;
    }

    public Criteria andUpdateUserNotEqualTo(String value) {
        addCriterion("update_user <>", value, "updateUser");
        return (Criteria)this;
    }

    public Criteria andUpdateUserGreaterThan(String value) {
        addCriterion("update_user >", value, "updateUser");
        return (Criteria)this;
    }

    public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
        addCriterion("update_user >=", value, "updateUser");
        return (Criteria)this;
    }

    public Criteria andUpdateUserLessThan(String value) {
        addCriterion("update_user <", value, "updateUser");
        return (Criteria)this;
    }

    public Criteria andUpdateUserLessThanOrEqualTo(String value) {
        addCriterion("update_user <=", value, "updateUser");
        return (Criteria)this;
    }

    public Criteria andUpdateUserLike(String value) {
        addCriterion("update_user like", value, "updateUser");
        return (Criteria)this;
    }

    public Criteria andUpdateUserNotLike(String value) {
        addCriterion("update_user not like", value, "updateUser");
        return (Criteria)this;
    }

    public Criteria andUpdateUserIn(List<String> values) {
        addCriterion("update_user in", values, "updateUser");
        return (Criteria)this;
    }

    public Criteria andUpdateUserNotIn(List<String> values) {
        addCriterion("update_user not in", values, "updateUser");
        return (Criteria)this;
    }

    public Criteria andUpdateUserBetween(String value1, String value2) {
        addCriterion("update_user between", value1, value2, "updateUser");
        return (Criteria)this;
    }

    public Criteria andUpdateUserNotBetween(String value1, String value2) {
        addCriterion("update_user not between", value1, value2, "updateUser");
        return (Criteria)this;
    }

    public Criteria andIsDeleteIsNull() {
        addCriterion("is_delete is null");
        return (Criteria)this;
    }

    public Criteria andIsDeleteIsNotNull() {
        addCriterion("is_delete is not null");
        return (Criteria)this;
    }

    public Criteria andIsDeleteEqualTo(Byte value) {
        addCriterion("is_delete =", value, "isDelete");
        return (Criteria)this;
    }

    public Criteria andIsDeleteNotEqualTo(Byte value) {
        addCriterion("is_delete <>", value, "isDelete");
        return (Criteria)this;
    }

    public Criteria andIsDeleteGreaterThan(Byte value) {
        addCriterion("is_delete >", value, "isDelete");
        return (Criteria)this;
    }

    public Criteria andIsDeleteGreaterThanOrEqualTo(Byte value) {
        addCriterion("is_delete >=", value, "isDelete");
        return (Criteria)this;
    }

    public Criteria andIsDeleteLessThan(Byte value) {
        addCriterion("is_delete <", value, "isDelete");
        return (Criteria)this;
    }

    public Criteria andIsDeleteLessThanOrEqualTo(Byte value) {
        addCriterion("is_delete <=", value, "isDelete");
        return (Criteria)this;
    }

    public Criteria andIsDeleteIn(List<Byte> values) {
        addCriterion("is_delete in", values, "isDelete");
        return (Criteria)this;
    }

    public Criteria andIsDeleteNotIn(List<Byte> values) {
        addCriterion("is_delete not in", values, "isDelete");
        return (Criteria)this;
    }

    public Criteria andIsDeleteBetween(Byte value1, Byte value2) {
        addCriterion("is_delete between", value1, value2, "isDelete");
        return (Criteria)this;
    }

    public Criteria andIsDeleteNotBetween(Byte value1, Byte value2) {
        addCriterion("is_delete not between", value1, value2, "isDelete");
        return (Criteria)this;
    }
}
