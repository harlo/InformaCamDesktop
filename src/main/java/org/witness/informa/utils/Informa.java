package org.witness.informa.utils;

import java.io.File;

public interface Informa extends Constants {
	public void extractMetadata();
	public void extractMetadata(String md);
	public int getIntegrityRating();
	public File createClone(File dir, File original, String cloneName);
}
