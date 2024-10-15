package com.shadi.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExtraResponse<X> {
	private String responseKey;
	private X responseValue;
	@SuppressWarnings("unused")
	private ExtraResponse() {}

}
