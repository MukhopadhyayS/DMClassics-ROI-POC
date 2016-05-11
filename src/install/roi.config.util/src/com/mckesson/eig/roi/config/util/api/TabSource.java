/**
 *
 */
package com.mckesson.eig.roi.config.util.api;

/**
 * @author Shah Mohamed.N
 *
 */
public enum TabSource {

	ROI("ROI"),
	HPFW("HPFW"),
	ROIDB("ROIDB"),
	DB("DB"),
	OUTPUT("OUTPUT"),
	OUTPUTDB("OUTPUTDB"),
	FAX("FAX"),
	CLIENT_ROI("CLIENT_ROI"),
	HPFWDB("HPFWDB"),
	CLIENT_OUTPUT("CLIENT_OUTPUT");

	private final String _source;
	TabSource(String source) {
		_source = source;
	}

	@Override
	public String toString() {
		return _source;
	}
}
