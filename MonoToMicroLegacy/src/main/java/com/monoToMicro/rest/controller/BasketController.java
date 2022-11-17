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

package com.monoToMicro.rest.controller;

import com.monoToMicro.core.events.ReadimagesBasketEvent;

import com.monoToMicro.core.events.imagesReadBasketEvent;
import com.monoToMicro.core.events.imagesWriteBasketEvent;
import com.monoToMicro.core.events.WriteimagesBasketEvent;
import com.monoToMicro.core.model.Unicorn;
import com.monoToMicro.core.model.UnicornBasket;
import com.monoToMicro.core.services.imageservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 
 * @author nirozeri
 * 
 */
@RestController
@RequestMapping("/images/basket")
public class BasketController extends CoreController {

	@Autowired
	private imageservice imageservice;
	
	/**
	 *  
	 * @param unicornBasket
	 * @return
	 */
	@PreAuthorize("permitAll()")	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> addUnicornToBasket(@RequestBody UnicornBasket unicornBasket) {
				
		if(unicornBasket!=null && unicornBasket.getimages()!=null && !unicornBasket.getimages().isEmpty()) {
			
			String userUuid = unicornBasket.getUuid();		
			//Assuming only one Unicorn is added each time
			String unicornUuid = unicornBasket.getimages().get(0).getUuid();		
			
			WriteimagesBasketEvent writeimagesBasketEvent = new WriteimagesBasketEvent(userUuid, unicornUuid);
			imagesWriteBasketEvent imagesWriteBasketEvent = imageservice.addUnicornToBasket(writeimagesBasketEvent);

			if (imagesWriteBasketEvent.isStateSuccess()) {
				
				return new ResponseEntity<Void>(HttpStatus.OK);
			}
		}
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);				
	}

	@PreAuthorize("permitAll()")
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void> removeFromBasket(@RequestBody UnicornBasket unicornBasket) {
				
		if(unicornBasket!=null && unicornBasket.getimages()!=null && !unicornBasket.getimages().isEmpty()) {
			
			String userUuid = unicornBasket.getUuid();		
			//Assuming only one Unicorn is added each time
			String unicornUuid = unicornBasket.getimages().get(0).getUuid();
		
			WriteimagesBasketEvent writeimagesBasketEvent = new WriteimagesBasketEvent(userUuid, unicornUuid);
			imagesWriteBasketEvent imagesWriteBasketEvent = imageservice.removeUnicornFromBasket(writeimagesBasketEvent);

			if (imagesWriteBasketEvent.isStateSuccess()) {			
				return new ResponseEntity<Void>(HttpStatus.OK);
			}
		}
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);				
	}
	
	@PreAuthorize("permitAll()")
	@RequestMapping(method = RequestMethod.GET, value = "/{userUuid}")
	public ResponseEntity<UnicornBasket> getUnicornBasket(@PathVariable String userUuid) {
				
		ReadimagesBasketEvent readimagesBasketEvent = new ReadimagesBasketEvent(userUuid);
		imagesReadBasketEvent imagesReadBasketEvent = imageservice.getUnicornBasket(readimagesBasketEvent);

		if (imagesReadBasketEvent.isReadOK()) {
			List<Unicorn> images = imagesReadBasketEvent.getimages();
			UnicornBasket unicornBasket = new UnicornBasket();
			unicornBasket.setUuid(userUuid);
			unicornBasket.setimages(images);
			return new ResponseEntity<UnicornBasket>(unicornBasket, HttpStatus.OK);			
		}

		return new ResponseEntity<UnicornBasket>(HttpStatus.BAD_REQUEST);				
	}
}
