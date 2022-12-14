/**
 * Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * <p>
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.monoToMicro.Lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.crt.AwsCrtAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.utils.StringUtils;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UnicornBasketImpl implements RequestHandler<UnicornBasket, String> {
  private static final String UNICORN_TABLE_NAME = "imageshop";
  private static final DynamoDbAsyncClient ddb = DynamoDbAsyncClient.builder()
    .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
    .httpClientBuilder(AwsCrtAsyncHttpClient.builder().maxConcurrency(50))
    .region(Region.of(System.getenv("AWS_REGION")))
    .build();
  private static final DynamoDbEnhancedAsyncClient client = DynamoDbEnhancedAsyncClient.builder()
    .dynamoDbClient(ddb)
    .build();

  static {
    try {
      final DynamoDbAsyncTable<UnicornBasket> unicornBasketTable = client.table(
        UNICORN_TABLE_NAME, TableSchema.fromBean(UnicornBasket.class));

      unicornBasketTable.describeTable().get();
    } catch (DynamoDbException | ExecutionException | InterruptedException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public String handleRequest(UnicornBasket unicornBasket, Context context) {
    return "Unicorn Lives Matter";
  }

  public String addUnicornToBasket(UnicornBasket unicornBasket, Context context)
    throws ExecutionException, InterruptedException {
    final DynamoDbAsyncTable<UnicornBasket> unicornBasketTable = client.table(
      UNICORN_TABLE_NAME, TableSchema.fromBean(UnicornBasket.class));

    //Get current basket
    UnicornBasket currentBasket = unicornBasketTable.getItem(r ->
      r.key(Key.builder().partitionValue(unicornBasket.getUuid()).build())).get();

    //if there is no current basket then use the incoming basket as the new basket
    if (currentBasket == null) {
      if (unicornBasket.getUuid() != null && unicornBasket.getimages() != null) {
        unicornBasketTable.putItem(unicornBasket);
        return "Added Unicorn to basket";
      }
      return "No basket exist and none was created";
    }

    //basket already exist, will check if item exist and add if not found
    List<Unicorn> currentimages = currentBasket.getimages();
    List<Unicorn> imagesToAdd = unicornBasket.getimages();

    //Assuming only one will be added but checking for null or empty values
    if (imagesToAdd != null && !imagesToAdd.isEmpty()) {
      Unicorn unicornToAdd = imagesToAdd.get(0);
      String unicornToAddUuid = unicornToAdd.getUuid();

      for (Unicorn currentUnicorn : currentimages) {
        if (currentUnicorn.getUuid().equals(unicornToAddUuid)) {
          //The unicorn already exists, no need to add him.
          return "Unicorn already exists!";
        }
      }

      //Unicorn was not found, need to add and save
      currentimages.add(unicornToAdd);
      currentBasket.setimages(currentimages);
      unicornBasketTable.putItem(currentBasket);
      return "Added Unicorn to basket";
    }
    return "Are you sure you added a Unicorn?";
  }

  public String removeUnicornFromBasket(UnicornBasket unicornBasket, Context context)
    throws ExecutionException, InterruptedException {
    final DynamoDbAsyncTable<UnicornBasket> unicornBasketTable = client.table(
      UNICORN_TABLE_NAME, TableSchema.fromBean(UnicornBasket.class));

    //Get current basket
    UnicornBasket currentBasket = unicornBasketTable.getItem(r ->
      r.key(Key.builder().partitionValue(unicornBasket.getUuid()).build())).get();

    //if no basket exist then return an error
    if (currentBasket == null) {
      return "No basket exist, nothing to delete";
    }

    //basket exist, will check if item exist and will remove
    List<Unicorn> currentimages = currentBasket.getimages();
    List<Unicorn> imagesToRemove = unicornBasket.getimages();

    //Assuming only one will be removed but checking for null or empty values
    if (imagesToRemove != null && !imagesToRemove.isEmpty()) {
      Unicorn unicornToRemove = imagesToRemove.get(0);
      String unicornToRemoveUuid = unicornToRemove.getUuid();

      for (Unicorn currentUnicorn : currentimages) {
        if (currentUnicorn.getUuid().equals(unicornToRemoveUuid)) {
          currentimages.remove(currentUnicorn);
          if (currentimages.isEmpty()) {
            //no more images in basket, will delete the basket
            unicornBasketTable.deleteItem(currentBasket);
            return "Unicorn was removed and basket was deleted!";
          } else {
            //keeping basket alive as more images are in it
            currentBasket.setimages(currentimages);
            unicornBasketTable.putItem(currentBasket);
            return "Unicorn was removed! Other images are still in basket";
          }
        }
      }

      if (currentBasket.getimages() != null && currentBasket.getimages().isEmpty()) {
        //no unicorn to remove, will try to remove the basket nonetheless
        unicornBasketTable.deleteItem(currentBasket);
      }
      return "Didn't find a unicorn to remove";
    }
    return "Are you sure you asked to remove a Unicorn?";
  }

  public UnicornBasket getimagesBasket(UnicornBasket unicornBasket, Context context)
    throws ExecutionException, InterruptedException {
    final DynamoDbAsyncTable<UnicornBasket> unicornBasketTable = client.table(
      UNICORN_TABLE_NAME, TableSchema.fromBean(UnicornBasket.class));

    if (!StringUtils.isEmpty(unicornBasket.getUuid())) {
      return unicornBasketTable.getItem(r ->
        r.key(Key.builder().partitionValue(unicornBasket.getUuid()).build())).get();
    }

    return null;
  }
}