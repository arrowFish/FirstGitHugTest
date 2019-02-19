package com.yyc.testqpid.controller;

import com.yyc.testqpid.entity.SimpleSendMsgBody;
import com.yyc.testqpid.service.QpidService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Author: yyc
 * @Date: Create in 2019/1/25
 * @Description:
 */
@Api(value = "QpidController", description = "qpid测试管理")
@RestController
@RequestMapping("/")
public class QpidController {

	private static ExecutorService pool = Executors.newFixedThreadPool(16);

	@Autowired
	private QpidService qpidService;

	@ApiOperation(value = "发送一条qpid消息", httpMethod = "POST", notes = "发送一条qpid消息")
	@RequestMapping(value = "/simpleSendMsg", method = RequestMethod.POST)
	@ResponseBody
	public void simpleSendMsg(
			@ApiParam(name = "msgBody", value = "消息实体", required = true) @RequestParam(value = "msgBody", name = "msgBody") String msgBody
			, @ApiParam(name = "subject", value = "消息主题", required = true) @RequestParam(value = "subject", name = "subject") String subject
			, @ApiParam(name = "outTime", value = "超时时间，单位毫秒", required = false) @RequestParam(value = "outTime", name = "outTime") long outTime) {
		qpidService.sendMsg(msgBody, subject, outTime);
	}

	@ApiOperation(value = "单线程循环发送qpid消息", httpMethod = "POST", notes = "单线程循环发送qpid消息")
	@RequestMapping(value = "/circleSendMsg", method = RequestMethod.POST)
	@ResponseBody
	public void circleSendMsg(
			@ApiParam(name = "msgBody", value = "消息实体", required = true) @RequestParam(value = "msgBody", name = "msgBody") String msgBody
			, @ApiParam(name = "circleTime", value = "循环次数", required = true) @RequestParam(value = "circleTime", name = "circleTime") int circleTime
			, @ApiParam(name = "intervalTime", value = "每次循环间隔时间", required = true, example = "10000") @RequestParam(value = "intervalTime", name = "intervalTime") long intervalTime
			, @ApiParam(name = "subject", value = "消息主题", required = true) @RequestParam(value = "subject", name = "subject") String subject
			, @ApiParam(name = "outTime", value = "超时时间，单位毫秒", required = true, example = "10000") @RequestParam(value = "outTime", name = "outTime") long outTime) {

		for (int i = 0; i < circleTime; i++) {
			qpidService.sendMsg(msgBody, subject, outTime);
			if (0 == intervalTime) {
				continue;
			}

			try {
				Thread.sleep(intervalTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@ApiOperation(value = "多线程循环发送qpid消息", httpMethod = "POST", notes = "多线程循环发送qpid消息")
	@RequestMapping(value = "/moreThreadCircleSendMsg", method = RequestMethod.POST)
	@ResponseBody
	public void ThreadSendMsg(
			@ApiParam(name = "msgBody", value = "消息实体", required = true) @RequestParam(value = "msgBody", name = "msgBody") String msgBody
			, @ApiParam(name = "circleTime", value = "循环次数", required = true) @RequestParam(value = "circleTime", name = "circleTime") int circleTime
			, @ApiParam(name = "subject", value = "消息主题", required = true) @RequestParam(value = "subject", name = "subject") String subject
			, @ApiParam(name = "outTime", value = "超时时间，单位毫秒", required = true, example = "10000") @RequestParam(value = "outTime", name = "outTime") long outTime
			, @ApiParam(name = "threadNumber", value = "需要开启的线程数目", required = true) @RequestParam(value = "threadNumber", name = "threadNumber") int threadNumber) {

		ExecutorService pool = Executors.newFixedThreadPool(threadNumber);
		for (int i = 0; i < circleTime; i++) {
			pool.submit(new QpidRunnable(subject, outTime, msgBody));
		}
	}


	@ApiOperation(value = "test", httpMethod = "GET", notes = "test")
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String simpleSendMsg() {
		return "hello world";
	}


	public class QpidRunnable implements Runnable {
		private String subject;
		private long outTime;
		private String msg;

		public QpidRunnable(String subject, long outTime, String msg) {
			this.msg = msg;
			this.subject = subject;
			this.outTime = outTime;
		}

		@Override
		public void run() {
			qpidService.sendMsg(msg, subject, outTime);
		}
	}
}
