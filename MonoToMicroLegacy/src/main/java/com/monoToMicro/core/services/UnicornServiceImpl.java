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

package com.monoToMicro.core.services;

import com.monoToMicro.core.events.ReadimagesBasketEvent;
import com.monoToMicro.core.events.ReadimagesEvent;
import com.monoToMicro.core.events.imagesReadBasketEvent;
import com.monoToMicro.core.events.imagesReadEvent;
import com.monoToMicro.core.events.imagesWriteBasketEvent;
import com.monoToMicro.core.events.WriteimagesBasketEvent;
import com.monoToMicro.core.model.Unicorn;
import com.monoToMicro.core.model.UnicornBasket;
import com.monoToMicro.core.repository.UnicornRepository;
import java.util.*;
import org.springframework.stereotype.Service;


/**
 * 
 * @author nirozeri
 * 
 */
@Service
public class imageserviceImpl implements imageservice {

	private final UnicornRepository unicornRepository;

	/**
	 * 
	 * @param itemRepository
	 */
	public imageserviceImpl(final UnicornRepository uncironRepository) {
		this.unicornRepository = uncironRepository;
	}
	
	public imagesReadEvent getimages(ReadimagesEvent readimagesEvent) {
		
		List<Unicorn> images = unicornRepository.getimages();
			
		if (images != null) {
			imagesReadEvent imagesEvent = new imagesReadEvent(images,
			imagesReadEvent.State.SUCCESS);
			return imagesEvent;
		}
		return new imagesReadEvent(imagesReadEvent.State.FAILED);		
	}

	@Override
	public imagesWriteBasketEvent addUnicornToBasket(WriteimagesBasketEvent writeimagesBasketEvent) {
		
		String userUuid = writeimagesBasketEvent.getUserUuid();
		String unicornUuid = writeimagesBasketEvent.getUnicornUuid();
		boolean result = unicornRepository.addUnicornToBasket(userUuid, unicornUuid);
		
		if (result) {			
			return new imagesWriteBasketEvent(imagesReadEvent.State.SUCCESS);
		}
		return new imagesWriteBasketEvent(imagesReadEvent.State.FAILED);
	}

	@Override
	public imagesWriteBasketEvent removeUnicornFromBasket(WriteimagesBasketEvent writeimagesBasketEvent) {
		String userUuid = writeimagesBasketEvent.getUserUuid();
		String unicornUuid = writeimagesBasketEvent.getUnicornUuid();
		boolean result = unicornRepository.removeUnicornFromBasket(userUuid, unicornUuid);
		
		if (result) {			
			return new imagesWriteBasketEvent(imagesReadEvent.State.SUCCESS);
		}
		return new imagesWriteBasketEvent(imagesReadEvent.State.FAILED);
	}

	@Override
	public imagesReadBasketEvent getUnicornBasket(ReadimagesBasketEvent readimagesBasketEvent) {
		String userUUID = readimagesBasketEvent.getUserUUID();
		
		List<Unicorn> images = unicornRepository.getUnicornBasket(userUUID);
		
		if (images != null) {			
			return new imagesReadBasketEvent(images, imagesReadEvent.State.SUCCESS);
		}
		return new imagesReadBasketEvent(imagesReadEvent.State.FAILED);		
	}

	@Override
	public imagesReadBasketEvent getAllBaskets() {
		
		List<UnicornBasket> baskets = unicornRepository.getAllBaskets();
		
		if (baskets != null) {			
			imagesReadBasketEvent event = new imagesReadBasketEvent(imagesReadEvent.State.SUCCESS);
			event.setBaskets(baskets);
			return event;
		}
		return new imagesReadBasketEvent(imagesReadEvent.State.FAILED);
	}		
}
