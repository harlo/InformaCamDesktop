#!/usr/bin/env python
import pycurl, base64, json, time, os.path
from daemon import runner
from datetime import datetime

un = 'BovineJonie'
pwd = 'aGoodManIsHardToFind'
api_url = 'https://stream.twitter.com/1/statuses/sample.json'
tcache_path = '/var/www/tcache/recent.json'
log_path = '/var/www/logs/uploader_monitor.json'
log_template = '{"timestamp": "%s", "message": "%s", "process": "otp.py"}'

class Monitor():
	def __init__(self):
		self.stdin_path = '/dev/null'
		self.stdout_path = '/dev/tty'
		self.stderr_path = '/dev/tty'
		self.pidfile_path = '/tmp/otp_monitor.pid'
		self.pidfile_timeout = 10
	
	def run(self):
		while True:
			hoseSlurper = HoseSlurper()
			time.sleep(36000)	#every half hour!

class HoseSlurper:
	def __init__(self):
		self.caps = []
		curl = pycurl.Curl()
		curl.setopt(pycurl.URL, api_url)
		curl.setopt(pycurl.HTTPHEADER, ['Authorization: ' + base64.b64encode(un + ":" + pwd)])
		curl.setopt(pycurl.WRITEFUNCTION, self.tweet_callback)
		print '********* STARTING OTP SLURPER **********'
		try:
			curl.perform()
		except pycurl.error:
			self.burn_list()
		
	def burn_list(self):
		print '********* STOPPING SLURPER **********'
		f = open(tcache_path, "w")
		f.writelines(json.dumps(self.caps))
		f.close()
		
		f = open(log_path, "a")
		f.writelines(log_template % (str(datetime.now()), "refreshed available tcache"))
		f.close()
		
	def tweet_callback(self, buffer):
		if(buffer.startswith('{"delete"') == False):
			if(len(self.caps) <= 500):
				self.caps.append(json.loads(buffer))
			else:
				return -1
			
monitor = Monitor()
daemon_runner = runner.DaemonRunner(monitor)
daemon_runner.do_action()	