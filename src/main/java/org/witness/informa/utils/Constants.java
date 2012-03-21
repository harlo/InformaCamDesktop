package org.witness.informa.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public interface Constants {
	public final static String LOG = "InformaScratchbed: ";
	
	public final static class DC {
		public final static String RESPONSE = "response";
		
		public final static class Attempts {
			public final static String TAG = "attempt";
			public final static int LOAD_MEDIA = 100;
		}
		
	}
	
	public final static class Callback {
		public final static class CheckForFfmpeg {
			public final static int ID = 11;
			public final static String TAG = "checkForFfmpeg";
			public final static String LABEL = "Check Ffmpeg Status";
		}
		
		public final static class LoadMedia {
			public final static int ID = 10;
			public final static String TAG = "loadMedia";
			public final static String LABEL = "Load Media";
		}
		
		public final static class WriteMetadata {
			public final static int ID = 9;
			public final static String TAG = "writeMetadata";
			public final static String LABEL = "Write Metadata to Media";
		}
		
		public final static class Jpeg {
			public final static String GET_METADATA = "getMetadata";
			
		}
		
		public final static class ProgressWindow {
			public final static int ID = 8;
			public final static String TAG = "killProgress";
			public final static class Notifications {
				public final static String LOADING = "Loading...";
			}
		}
	}
	
	public final static class UI {
		public final static class Prompt {
			public final static String MEDIA_PICKER = "Jpeg or Movie";
		}
	}
	
	public final static class Media {
		public final static String PATH_START =  "/Users/LvH/Documents/Eclipse/workspace/InformaScratchbed/assets/";
		public final static String[] EXTENSIONS = {"jpeg", "jpg", "mov", "mp4", "mkv"};
		public final static class MimeTypes {
			public final static int JPEG = 100;
			public final static int MP4 = 101;
			public final static int MOV = 102;
			public final static int MKV = 103;
		}
		public final static Map<String, Integer> MIME_TYPES;
		static {
			Map<String, Integer> mime_types = new HashMap<String, Integer>();
			mime_types.put(".jpeg", Media.MimeTypes.JPEG);
			mime_types.put(".jpg", Media.MimeTypes.JPEG);
			mime_types.put(".mov", Media.MimeTypes.MOV);
			mime_types.put(".mp4", Media.MimeTypes.MP4);
			mime_types.put(".mkv", Media.MimeTypes.MKV);
			MIME_TYPES = Collections.unmodifiableMap(mime_types);
		}
		
		public final static String DIMENSIONS = "dimensions";
		
		public final static class Dimensions {
			public final static String WIDTH = "width";
			public final static String HEIGHT = "height";
		}
	}
	
	public final static class Ffmpeg {
		public final static String ROOT = "/usr/local/bin/";
		public final static String PID = "processId";
		
		public final static String CLONE = "%vpathclone_%vroot.mp4";
		public final static String METADATA = "%vpathinforma_metadata_%vroot.txt";
		
		public final static String CHECK = Ffmpeg.ROOT + "ffmpeg";
		public final static String GET_INFO = Ffmpeg.ROOT + "ffmpeg -i %vid";
		public final static String SHOW_STREAMS = Ffmpeg.ROOT + "ffprobe -pretty -show_streams %vid";
		public final static String EXTRACT_INFORMA_TRACK = Ffmpeg.ROOT + "ffmpeg -y -i %vid -an -vn -sbsf mov2textsub -scodec copy -f rawvideo %metadata";
		public final static String EMBED_INFORMA_TRACK = Ffmpeg.ROOT + "ffmpeg -y -i %vid -i %inft -scodec copy -vcodec copy -acodec copy %vpath%vroot.mkv";
		public final static String CLONE_FROM_MKV = Ffmpeg.ROOT + "ffmpeg -y -i %vid -vcodec copy -acodec copy %clone";
		
		public final static class Replace {
			public final static String VIDEO = "%vid";
			public final static String VIDEO_ROOT = "%vroot";
			public final static String VIDEO_PATH = "%vpath";
			public final static String CLONE = "%clone";
			public final static String METADATA = "%metadata";
		}
		
		public final static Map<String, String> TAGS;
		static {
			Map<String, String> tags = new HashMap<String, String>();
			tags.put("[STREAM]", "stream");
			TAGS = Collections.unmodifiableMap(tags);
		}
		
		public final static class Tags {
			public final static String END = "[/%tag]";
			public final static String RETURN = "result";
			public final static String TYPE = "type";
			
			public final static class Stream {
				public final static String CODEC_NAME = "codec_name";
				public final static String CODEC_TYPE = "codec_type";
				public final static String CODEC_TIME_BASE = "codec_time_base";
				public final static String CODEC_R_FRAME_RATE = "r_frame_rate";
				public final static String CODEC_AVG_FRAME_RATE = "avg_frame_rate";
				public final static String CODEC_NB_FRAMES = "nb_frames";
				public final static String CODEC_DURATION = "duration";
				public final static String CODEC_INDEX = "index";
				public final static String CODEC_WIDTH = "width";
				public final static String CODEC_HEIGHT = "height";
			}
		}
	}
}
