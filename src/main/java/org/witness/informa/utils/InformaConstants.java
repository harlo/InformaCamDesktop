package org.witness.informa.utils;

public interface InformaConstants {
		public final static String TAG = "************ INFORMA ***********";
		public final static String READOUT = "******************* INFORMA READOUT ******************";
		public final static String SUCKER_TAG = "******************** SUCKER SERVICE ******************";
		public final static String PW_EXPIRY = "**EXPIRED**";
		public final static int FROM_INFORMA_WIZARD = 3;
		public final static int FROM_INFORMA_TAGGER = 4;
		public final static int FROM_TRUSTED_DESTINATION_CHOOSER = 5;
		public final static int BLOB_MAX = 1048576;
		public final static String DUMP_FOLDER = "";
		public final static String TMP_FILE_NAME = "tmp_.jpg";
		public final static String NOT_INCLUDED = "NOT INCLUDED IN THIS VERSION";

		public final static class Keys {
			public final static String USER_CANCELED_EVENT = "userCanceledEvent";
			public final static String ENCRYPTED_IMAGES = "encryptedImages";
			
			public final static class Settings {
				public static final String INFORMA = "informa";
				public static final String SETTINGS_VIEWED = "informa.SettingsViewed";
				public static final String HAS_DB_PASSWORD = "informa.PasswordSet";
				public static final String DB_PASSWORD_CACHE_TIMEOUT = "informa.PasswordCacheTimeout";
				public static final String DEFAULT_IMAGE_HANDLING = "informa.DefaultImageHandling";
			}
			
			public final static class Service {
				public final static String STOP_SERVICE = "stopService";
				public final static String SET_CURRENT = "setCurrent";
				public final static String SEAL_LOG = "sealLog";
				public final static String GENERATE_IMAGE = "generateImage";
				public final static String IMAGES_GENERATED = "imagesGenerated";
				public final static String SET_EXIF = "setExif";
				public final static String FINISH_ACTIVITY = "finishActivity";
				public final static String START_SERVICE = "startService";
				public final static String LOCK_LOGS = "lockLogs";
				public final static String UNLOCK_LOGS = "unlockLogs";
				public final static String INFLATE_VIDEO_TRACK = "inflateDataForVideoTrack";
			}
			
			public final static class Informa {
				public final static String INTENT = "intent";
				public final static String GENEALOGY = "genealogy";
				public final static String DATA = "data";
			}
			
			public final static class CaptureEvent {
				public final static String TYPE = "captureEvent";
				public final static String MATCH_TIMESTAMP = "matchTimestamp";
				public final static String TIMESTAMP = Image.TIMESTAMP;
				public final static String ON_VIDEO_START = "timestampOnVideoStart";
				public final static String MEDIA_CAPTURE_COMPLETE = "mediaCapturedComplete";
				public final static String VIDEO_TRACK = Video.VIDEO_TRACK;
				public final static String EVENT = Events.CAPTURE_EVENT;
			}
			
			public final static class ImageRegion {
				public final static String INDEX = "regionIndex";
				public final static String THUMBNAIL = "regionThumbnail";
				public static final String DATA = "region_data";
				public final static String TIMESTAMP = "timestampOnGeneration";
				public final static String LOCATION = "locationOnGeneration";
				public final static String TAGGER_RETURN = "taggerReturned";
				public final static String FILTER = "obfuscationType";
				public final static String COORDINATES = "regionCoordinates";
				public final static String DIMENSIONS = "regionDimensions";
				public final static String WIDTH = "region_width";
				public final static String HEIGHT = "region_height";
				public final static String TOP = "region_top";
				public final static String LEFT = "region_left";
				public final static String UNREDACTED_DATA = "unredactedRegionData";
				public final static String BASE = "base";
				
				public final static class Data {
					public final static String UNREDACTED_HASH = "unredactedRegionHash";
					public final static String LENGTH = "dataLength";
					public final static String POSITION = "byteStart";
					public final static String BYTES = "byteArray";
				}
				
				public final static class Subject {
					public final static String PSEUDONYM = "subject_pseudonym";
					public final static String INFORMED_CONSENT_GIVEN = "subject_informedConsentGiven";
					public final static String PERSIST_FILTER = "subject_persistFilter";
				}
				
				public final static class Filter {
					public final static String PIXELATE = "org.witness.ssc.image.filters.PixelizeObscure";
					public final static String BACKGROUND_PIXELATE = "org.witness.ssc.image.filters.CrowdPixelizeObscure";
					public final static String REDACT = "org.witness.ssc.image.filters.SolidObscure";
					public final static String IDENTIFY = "org.witness.ssc.image.filters.InformaTagger";
				}
			}
			
			public final static class Data {
				public final static String IMAGE_REGIONS = "imageRegions";
				public final static String VIDEO_REGIONS = "videoRegions";
				public final static String EVENTS = "events";
				public final static String MEDIA_HASH = "mediaHash";
			}
			
			public final static class Genealogy {
				public final static String LOCAL_MEDIA_PATH = "localMediaPath";
				public final static String DATE_CREATED = "dateCreated";
			}
			
			public final static class Location {
				public final static String TYPE = "location_type";
				public final static String COORDINATES = "location_gpsCoordinates";
				public final static String CELL_ID = Suckers.Phone.CELL_ID;
			}
			
			public final static class Intent {
				public final static String ENCRYPT_LIST = "encryptList";
				public final static String INTENDED_DESTINATION = "intendedDestination";
				public final static class Destination {
					public final static String EMAIL = "destinationEmail";
					public final static String DISPLAY_NAME = "displayName";
				}
			}
			
			public final static class Events {
				public final static String EVENT_DATA = "eventData";
				public final static String CAPTURE_EVENT = CaptureEvent.TYPE;
				public final static String TYPE = "eventType";
				public final static String TIMESTAMP = CaptureEvent.TIMESTAMP;
			}
			
			public final static class TrustedDestinations {
				public final static String EMAIL = Intent.Destination.EMAIL;
				public final static String KEYRING_ID = "keyringId";
				public final static String DISPLAY_NAME = Intent.Destination.DISPLAY_NAME;
			}
			
			public final static class Media {
				public final static String MEDIA_TYPE = "source_type";
				public final static String UNREDACTED_HASH = Image.UNREDACTED_IMAGE_HASH;
				public final static String REDACTED_HASH = Image.REDACTED_IMAGE_HASH;
			}
			
			public final static class Image {
				public static final String METADATA = "source_metadata";
				public static final String CONTAINMENT_ARRAY = "source_containmentArray";
				public static final String UNREDACTED_IMAGE_HASH = "source_unredactedImageHash";
				public static final String REDACTED_IMAGE_HASH = "source_redactedImageHash";
				public final static String LOCAL_MEDIA_PATH = Genealogy.LOCAL_MEDIA_PATH;
				public final static String TIMESTAMP = "timestamp";
				public final static String LOCATION_OF_ORIGINAL = "source_locationOfOriginal";
				public final static String LOCATION_OF_OBSCURED_VERSION = "source_locationOfObscuredVersion";
				public final static String EXIF = "exifData";
				public final static String DIMENSIONS = "imageDimensions";
			}
			
			public final static class Video {
				public final static String FIRST_TIMESTAMP = CaptureEvent.ON_VIDEO_START;
				public final static String DURATION = "videoDuration";
				public final static String VIDEO_TRACK = "videoTrack";
			}
			
			public final static class Ass {
				public final static String VROOT = "%vroot";
				public final static String BLOCK_START = "%blockstart";
				public final static String BLOCK_END = "%blockend";
				public final static String BLOCK_DATA = "%mdload";
			}
			
			public final static class Owner {
				public static final String SIG_KEY_ID = "owner_sigKeyID";
				public static final String DEFAULT_SECURITY_LEVEL = "owner_defaultSecurityLevel";
				public static final String OWNERSHIP_TYPE = "owner_ownershipType";
			}
			
			public final static class Device {
				public static final String LOCAL_TIMESTAMP = "device_localTimestamp";
				public static final String PUBLIC_TIMESTAMP = "device_publicTimestamp";
				public static final String INTEGRITY = "integrityRating";
				public static final String IMEI = Suckers.Phone.IMEI;
				public static final String BLUETOOTH_DEVICE_NAME = Suckers.Phone.BLUETOOTH_DEVICE_NAME;
				public static final String BLUETOOTH_DEVICE_ADDRESS = Suckers.Phone.BLUETOOTH_DEVICE_ADDRESS;
			}
			
			public final static class Tables {
				public static final String IMAGES = "informaImages";
				public static final String CONTACTS = "informaContacts";
				public static final String SETUP = "informaSetup";
				public static final String IMAGE_REGIONS = "imageRegions";
				public static final String TRUSTED_DESTINATIONS = "trustedDestinations";
				public static final String ENCRYPTED_IMAGES = "encryptedImages";
			}
			
			public final static class Suckers {
				public final static String PHONE = "Suckers_Phone";
				public final static String ACCELEROMETER = "Suckers_Accelerometer";
				public final static String GEO = "Suckers_Geo";
							
				public final static class Accelerometer {
					public final static String ACC = "acc";
					public final static String ORIENTATION = "orientation";
					public final static String LIGHT = "lightMeter";
					public final static String X = "acc_x";
					public final static String Y = "acc_y";
					public final static String Z = "acc_z";
					public final static String AZIMUTH = "orientation_azimuth";
					public final static String PITCH = "orientation_pitch";
					public final static String ROLL = "orientation_roll";
					public final static String LIGHT_METER_VALUE = "lightMeter_value";
				}
				
				public final static class Geo {
					public final static String GPS_COORDS = Location.COORDINATES;
				}
				
				public final static class Phone {
					public final static String CELL_ID = "location_cellId";
					public final static String IMEI = "device_imei";
					public final static String BLUETOOTH_DEVICE_NAME = "device_bluetooth_name";
					public final static String BLUETOOTH_DEVICE_ADDRESS = "device_bluetooth_address";
				}
			}
			
			/*
			public final static class Exif {
				public final static String MAKE = ExifInterface.TAG_MAKE;
				public final static String MODEL = ExifInterface.TAG_MODEL;
				public final static String APERTURE = ExifInterface.TAG_APERTURE;
				public final static String FLASH = ExifInterface.TAG_FLASH;
				public final static String EXPOSURE = ExifInterface.TAG_EXPOSURE_TIME;
				public final static String FOCAL_LENGTH = ExifInterface.TAG_FOCAL_LENGTH;
				public final static String IMAGE_WIDTH = ExifInterface.TAG_IMAGE_WIDTH;
				public final static String IMAGE_LENGTH = ExifInterface.TAG_IMAGE_LENGTH;
				public final static String ISO = ExifInterface.TAG_ISO;
				public final static String ORIENTATION = ExifInterface.TAG_ORIENTATION;
				public final static String WHITE_BALANCE = ExifInterface.TAG_WHITE_BALANCE;
			}
			*/
			
		}
		
		public final static class MediaTypes {
			public final static int PHOTO = 101;
			public final static int VIDEO = 102;
		}
		
		public final static class CaptureEvents {
			public final static int MEDIA_CAPTURED = 5;
			public final static int MEDIA_SAVED = 6;
			public final static int REGION_GENERATED = 7;
			public final static int EXIF_REPORTED = 8;
			public final static int BLUETOOTH_DEVICE_SEEN = 9;
			public final static int VALUE_CHANGE = 4;
			public final static int DURATIONAL_LOG = 3;
		}

		public final static class LocationTypes {
			public final static int ON_MEDIA_CAPTURED = 10;
			public final static int ON_MEDIA_SAVED = 11;
			public final static int ON_REGION_GENERATED = 12;
			public final static int ON_VIDEO_START = 13;
		}
		
		public final static class CaptureTimestamps {
			public final static int ON_MEDIA_CAPTURED = LocationTypes.ON_MEDIA_CAPTURED;
			public final static int ON_MEDIA_SAVED = LocationTypes.ON_MEDIA_SAVED;
			public final static int ON_REGION_GENERATED = LocationTypes.ON_REGION_GENERATED;
			public final static int ON_VIDEO_START = LocationTypes.ON_VIDEO_START;
		}

		public final static class SecurityLevels {
			public final static int UNENCRYPTED_SHARABLE = 100;
			public final static int UNENCRYPTED_NOT_SHARABLE = 101;
		}
		
		public final static class LoginCache {
			public final static int ALWAYS = 200;
			public final static int AFTER_SAVE = 201;
			public final static int ON_CLOSE = 202;
		}
		
		public final static class OriginalImageHandling {
			public final static int LEAVE_ORIGINAL_ALONE = 300;
			public final static int ENCRYPT_ORIGINAL = 301;
			public final static int DELETE_ORIGINAL = 302;
		}
		
		public final static class Device {
			public final static int IS_SELF = -1;
			public final static int IS_NEIGHBOR = 1;
		}
		
		public final static class Owner {
			public final static int INDIVIDUAL = 400;
		}
		
		public final static class Consent {
			public final static int GENERAL = 101;
		}
		
		public final static int NOT_REPORTED = -1;
}
