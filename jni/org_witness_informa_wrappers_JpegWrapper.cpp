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

JNIEXPORT jint JNICALL Java_org_witness_informa_wrappers_JpegWrapper_unpackRegion
  (JNIEnv *env, jobject obj, jstring fileName, jstring cloneFileName, jbyteArray redactedBytes, int length) {
	const char* jpegFileName;
	const char* jpegCloneFileName;

	char* rBytes = new char[length];
	env->GetByteArrayRegion(redactedBytes, (jint)0, (jint)length, (jbyte*)rBytes);

	std::vector<unsigned char> redactionPack(length);
	memcpy(&redactionPack[0], rBytes, length);

	jpegFileName = (env)->GetStringUTFChars(fileName, NULL);
	jpegCloneFileName = (env)->GetStringUTFChars(cloneFileName, NULL);

	jpeg_redaction::Jpeg jpeg;
	if(!jpeg.LoadFromFile(jpegFileName, true)) {
		return 0;
	}

	jpeg_redaction::Redaction unpackedRegion;
	unpackedRegion.Unpack(redactionPack);
	jpeg.ReverseRedaction(unpackedRegion);

	jpeg.Save(jpegCloneFileName);

	return 1;
}
