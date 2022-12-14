/**
 * Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.monoToMicro.core.events;

/**
 * 
 * @author nirozeri
 * 
 */
public class WriteimagesBasketEvent extends ReadEvent {
	
	private String userUuid = null;
	private String unicornUuid = null;
	
	/**
	 * 
	 * @param userUuid
	 */
	public WriteimagesBasketEvent(String userUuid) {
		this.setUserUuid(userUuid);
		
	}
	
	/**
	 * 
	 * @param userUuid
	 * @param unicornUuid
	 */
	public WriteimagesBasketEvent(String userUuid, String unicornUuid) {
		this.setUserUuid(userUuid);
		this.setUnicornUuid(unicornUuid);
	}

	/**
	 * 
	 * @return
	 */
	public String getUserUuid() {
		return userUuid;
	}

	/**
	 * 
	 * @param userUuid
	 */
	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	/**
	 * 
	 * @return
	 */	
	public String getUnicornUuid() {
		return unicornUuid;
	}

	/**
	 * 
	 * @param unicornUuid
	 */
	public void setUnicornUuid(String unicornUuid) {
		this.unicornUuid = unicornUuid;
	}
}
