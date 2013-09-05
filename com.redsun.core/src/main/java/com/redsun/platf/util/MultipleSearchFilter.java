package com.redsun.platf.util;

import com.redsun.platf.util.search.SearchFilterRule;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2012/12/7
 * Time: 下午 2:31
 * To change this template use File | Settings | File Templates.
 * @author  pyc :重查詢條件
 *
 */
 //filter:
    //  {"groupOp":"OR","rules":[
    //        {"field":"name","op":"le","data":"CP"},
    //        {"field":"lastUpdatedBy","op":"eq","data":"EPSYS"},
    //        {"field":"name","op":"eq","data":"EPSYS"}
    //    ]}
public class MultipleSearchFilter implements Serializable {

    private static final long serialVersionUID = 111L;

    private String groupOp;
    private List<SearchFilterRule> rules=new ArrayList<SearchFilterRule>();

    public String getGroupOp() {
        return groupOp;
    }

    public void setGroupOp(String groupOp) {
        this.groupOp = groupOp;
    }

    public List<SearchFilterRule> getRules() {
        return rules;
    }

    public void setRules(List<SearchFilterRule> rules) {
        this.rules = rules;
    }

    @Override
      	public String toString() {
      		return ToStringBuilder.reflectionToString(this);
      	}

    public String jsonString() throws IOException {
        return JsonUtils.toJsonString(this);
    }

}

