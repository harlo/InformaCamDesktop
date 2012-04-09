#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <vector>

#include "jpeg.h"
#include "redaction.h"
#include "debug_flag.h"
#include "obscura_metadata.h"

#include <jni.h>
#include "org_witness_informa_wrappers_JpegWrapper.h"

JNIEXPORT jstring JNICALL Java_org_witness_informa_wrappers_JpegWrapper_getMetadata
  (JNIEnv *env, jobject obj, jstring fileName) {
	unsigned int length = 0;
	const char* jpegFileName;
	jpegFileName = (env)->GetStringUTFChars(fileName, NULL);

	jpeg_redaction::Jpeg jpeg;
	if(!jpeg.LoadFromFile(jpegFileName, true)) {
		exit(1);
	}

	const unsigned char *metadataBlob = jpeg.GetObscuraMetaData(&length);
	jstring out = env->NewStringUTF(reinterpret_cast<const char*>(metadataBlob));

	return out;
}

/*
JNIEXPORT jbyteArray JNICALL Java_org_witness_informa_wrappers_JpegWrapper_unpackRegion
  (JNIEnv *, jobject, jstring, jint, jint) {

}
*/

JNIEXPORT jstring JNICALL Java_org_witness_informa_wrappers_JpegWrapper_sayHi
  (JNIEnv *env, jobject obj) {

	jstring out = env->NewStringUTF("hello fromt he jni wrapper");

	return out;
}
