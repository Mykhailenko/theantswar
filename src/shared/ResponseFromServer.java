package shared;

import java.io.Serializable;

import server.model.Locality;

public class ResponseFromServer implements Serializable{
	private static final long serialVersionUID = 3715051751516683440L;
	private Locality locality;
	public Locality getLocality() {
		return locality;
	}
	public void setLocality(Locality locality) {
		this.locality = locality;
	}
	
}
