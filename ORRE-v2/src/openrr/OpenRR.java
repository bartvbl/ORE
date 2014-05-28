package openrr;

import orre.api.ORRE;

public class OpenRR {

	public static void main(String[] args) {
		ORRE engine = ORRE.init(args, "OpenRR");
		engine.run();
	}

}
