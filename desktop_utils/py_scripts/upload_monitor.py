#!/usr/bin/env python
import sys, pycurl, os, cStringIO, json, subprocess, time, hashlib
from daemon import runner
from datetime import datetime

submissions_root = '/var/www/submissions/'
messages_root = '/var/www/messages/'
web_root = '/var/www/'
new_submission_query = 'submissions/'
get_unregistered_query = 'upload_tracker/_design/tracker/_view/bytes_transfered'
remove_submission_query = 'upload_tracker/%s?rev=%s'
log_template = '{"timestamp": %s, "entity": {"id": %s, "rev": %s}, "message": %s, "process": "upload_monitor.py"}'

def hashFile(filename, block_size=2**14):
	md5 = hashlib.md5()
	f = open(filename)
	while True:
		data = f.read(block_size)
		if not data:
			break
		md5.update(data)
	f.close()
	return md5.hexdigest()

class Submission(object):
	def __init__(self, hashed_key, media_type, timestamp_received, timestamp_submitted):
		self.hashed_key = hashed_key
		self.media_type = media_type
		self.timestamp_received = timestamp_received
		self.timestamp_submitted = timestamp_submitted
		
	def moveAttachment(self, oldAttachment):
		make_dir = subprocess.Popen(["mkdir", submissions_root + self.hashed_key], stdout=subprocess.PIPE)
		out = make_dir.communicate()[0]
		# if out is not "", that folder already existed...
		# make of that what you will
		
		move_item = subprocess.Popen(["cp", web_root + oldAttachment, submissions_root + self.hashed_key + "/" + oldAttachment.split('/')[len(oldAttachment.split('/')) - 1]], stdout=subprocess.PIPE)
		out = move_item.communicate()[0]
		if out == "":
			# hash the file and create its folder
			make_hashfile = subprocess.Popen(["mkdir", messages_root + hashFile(oldAttachment)], stdout=subprocess.PIPE)
			out = make_hashfile.communicate()[0]
			return True
		else:
			return False
		
	def removeUpload(self, id, rev):
		curl = DoCurl((remove_submission_query % (id, rev)).__str__())
		curl.setMethod('DELETE')
		return curl.perform()
				

class DoCurl(object):
	def __init__(self, query):
		self.buf = cStringIO.StringIO()
		curl = pycurl.Curl()
		curl.setopt(pycurl.URL, 'http://admin:narnia39@localhost:5984/' + query)
		curl.setopt(pycurl.WRITEFUNCTION, self.buf.write)
		self.curl = curl
		
	def setMethod(self, method):
		self.curl.setopt(pycurl.CUSTOMREQUEST, method)
	
	def perform(self):
		self.curl.perform()
		b_string = self.buf.getvalue()
		self.buf.flush()
		self.buf.close()
		return json.loads(b_string)
		
class Monitor():
	def __init__(self):
		self.stdin_path = '/dev/null'
		self.stdout_path = '/dev/tty'
		self.stderr_path = '/dev/tty'
		self.pidfile_path = '/tmp/uploader_monitor.pid'
		self.pidfile_timeout = 10
		self.logfile_path = '/var/www/logs/uploader_monitor.json'
	
	def run(self):
		while True:
			curl = DoCurl(get_unregistered_query)
			j = curl.perform()
			rows = j['rows']
			if len(rows) > 0:
				LOG = open(self.logfile_path,"w")
				for row in rows:
					if row['value']['bytes_expected'] == row['value']['bytes_transfered']:
						# turn each row into a submission (and insert it)
						vals = row['value']
						sub = Submission(vals['device_pgp'], vals['media_type'], vals['timestamp_received'], vals['timestamp_submitted'])
						if sub.moveAttachment(vals['attachment']) == True:
							remove = sub.removeUpload(vals['_id'], vals['_rev'])
							if remove['ok'] == True:
								log_content = (log_template % (str(datetime.now()), vals['_id'], vals['_rev'], 'Media removed from upload queue')).__str__()
								LOG.write(log_content)
						
						else:
							log_content = (log_template % (str(datetime.now()), vals['_id'], vals['_rev'], 'Could not copy uploaded file')).__str__()
							LOG.write(log_content)						
				LOG.close()
			time.sleep(10)
				
monitor = Monitor()
daemon_runner = runner.DaemonRunner(monitor)
daemon_runner.do_action()