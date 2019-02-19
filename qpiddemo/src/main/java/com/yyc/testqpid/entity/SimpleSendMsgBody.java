package com.yyc.testqpid.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: yyc
 * @Date: Create in 2019/1/25
 * @Description:
 */
@ApiModel(value = "SimpleSendMsgBody", description = "消息实体")
public class SimpleSendMsgBody {

	@ApiModelProperty(value = "消息实体", example = "{\\\"name\\\":\\\"nihao\\\"}")
	private String body;

	/*@ApiModelProperty(value = "消息主题", example = "yyc.test")
	private String subject;

	@ApiModelProperty(value = "超时时间", example = "10000")
	private long outTime;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}*/

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
/*

	public long getOutTime() {
		return outTime;
	}

	public void setOutTime(long outTime) {
		this.outTime = outTime;
	}
*/

	@Override
	public String toString() {
		return "SimpleSendMsgBody{" +
				"body='" + body + '\'' +
				'}';
	}
}
