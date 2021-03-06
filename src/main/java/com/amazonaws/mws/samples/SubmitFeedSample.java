/******************************************************************************* 
 *  Copyright 2009 Amazon Services.
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *
 *  You may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at: http://aws.amazon.com/apache2.0
 *  This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 *  CONDITIONS OF ANY KIND, either express or implied. See the License for the 
 *  specific language governing permissions and limitations under the License.
 * ***************************************************************************** 
 *
 *  Marketplace Web Service Java Library
 *  API Version: 2009-01-01
 *  Generated: Wed Feb 18 13:28:48 PST 2009 
 *
 */

package com.amazonaws.mws.samples;

import java.io.*;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import amazclient.xmlmodel.Shoes;
import com.amazonaws.mws.*;
import com.amazonaws.mws.model.*;
import com.amazonaws.mws.mock.MarketplaceWebServiceMock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Submit Feed Samples
 */
public class SubmitFeedSample {

    static final Log log = LogFactory.getLog(SubmitFeedSample.class);

    /**
     * Just add a few required parameters, and try the service Submit Feed
     * functionality
     *
     * @param args
     *            unused
     */
    /**
     * @param args
     */
    public static void main(String... args) throws JAXBException, IOException {

        /************************************************************************
         * Access Key ID and Secret Access Key ID, obtained from:
         * http://aws.amazon.com
         ***********************************************************************/
        final String accessKeyId = "AKIAJDR2N32XSM5HT2ZQ";
        final String secretAccessKey = "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";
        final String appName = "test_app";
        final String appVersion = "1.0";

        MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();

        config.setServiceURL("https://mws.amazonservices.com.cn");

        MarketplaceWebService service = new MarketplaceWebServiceClient(
                accessKeyId, secretAccessKey, appName, appVersion, config);

        final String merchantId = "AAHKV2X7AFYLW";

        final IdList marketplaces = new IdList(Arrays.asList(
                "Marketplae1",
                "Marketplace2"));

        SubmitFeedRequest request = new SubmitFeedRequest();
        request.setMerchant(merchantId);
//        request.setMarketplaceIdList(marketplaces);

        request.setFeedType("_POST_ITEM_DATA_");

        // MWS exclusively offers a streaming interface for uploading your
        // feeds. This is because
        // feed sizes can grow to the 1GB+ range - and as your business grows
        // you could otherwise
        // silently reach the feed size where your in-memory solution will no
        // longer work, leaving you
        // puzzled as to why a solution that worked for a long time suddenly
        // stopped working though
        // you made no changes. For the same reason, we strongly encourage you
        // to generate your feeds to
        // local disk then upload them directly from disk to MWS via Java -
        // without buffering them in Java
        // memory in their entirety.
        // Note: MarketplaceWebServiceClient will not retry a submit feed request
        // because there is no way to reset the InputStream from our client. 
        // To enable retry, recreate the InputStream and resubmit the feed
        // with the new InputStream. 
        //
        // request.setFeedContent( new FileInputStream("my-feed.xml" /*or
        // "my-flat-file.txt" if you use flat files*/);

        OutputStream out = new FileOutputStream("tmp_file.xml");

        JAXBContext jaxbContext = JAXBContext.newInstance("amazclient.xmlmodel");

        final Shoes shoes = new Shoes();
        shoes.setClothingType("1");
        shoes.setVariationData(new Shoes.VariationData());
        Shoes.ClassificationData classificationData = new Shoes.ClassificationData();

        shoes.setClassificationData(classificationData);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(shoes, out);
        out.close();

        InputStream in = new FileInputStream("tmp_file.xml");
        request.setFeedContent(in);
        invokeSubmitFeed(service, request);

    }

    /**
     * Submit Feed request sample Uploads a file for processing together with
     * the necessary metadata to process the file, such as which type of feed it
     * is. PurgeAndReplace if true means that your existing e.g. inventory is
     * wiped out and replace with the contents of this feed - use with caution
     * (the default is false).
     *
     * @param service instance of MarketplaceWebService service
     * @param request Action to invoke
     */
    public static void invokeSubmitFeed(MarketplaceWebService service,
                                        SubmitFeedRequest request) {
        try {

            SubmitFeedResponse response = service.submitFeed(request);

            System.out.println("SubmitFeed Action Response");
            System.out
                    .println("=============================================================================");
            System.out.println();

            System.out.print("    SubmitFeedResponse");
            System.out.println();
            if (response.isSetSubmitFeedResult()) {
                System.out.print("        SubmitFeedResult");
                System.out.println();
                SubmitFeedResult submitFeedResult = response
                        .getSubmitFeedResult();
                if (submitFeedResult.isSetFeedSubmissionInfo()) {
                    System.out.print("            FeedSubmissionInfo");
                    System.out.println();
                    FeedSubmissionInfo feedSubmissionInfo = submitFeedResult
                            .getFeedSubmissionInfo();
                    if (feedSubmissionInfo.isSetFeedSubmissionId()) {
                        System.out.print("                FeedSubmissionId");
                        System.out.println();
                        System.out.print("                    "
                                + feedSubmissionInfo.getFeedSubmissionId());
                        System.out.println();
                    }
                    if (feedSubmissionInfo.isSetFeedType()) {
                        System.out.print("                FeedType");
                        System.out.println();
                        System.out.print("                    "
                                + feedSubmissionInfo.getFeedType());
                        System.out.println();
                    }
                    if (feedSubmissionInfo.isSetSubmittedDate()) {
                        System.out.print("                SubmittedDate");
                        System.out.println();
                        System.out.print("                    "
                                + feedSubmissionInfo.getSubmittedDate());
                        System.out.println();
                    }
                    if (feedSubmissionInfo.isSetFeedProcessingStatus()) {
                        System.out
                                .print("                FeedProcessingStatus");
                        System.out.println();
                        System.out.print("                    "
                                + feedSubmissionInfo.getFeedProcessingStatus());
                        System.out.println();
                    }
                    if (feedSubmissionInfo.isSetStartedProcessingDate()) {
                        System.out
                                .print("                StartedProcessingDate");
                        System.out.println();
                        System.out
                                .print("                    "
                                        + feedSubmissionInfo
                                        .getStartedProcessingDate());
                        System.out.println();
                    }
                    if (feedSubmissionInfo.isSetCompletedProcessingDate()) {
                        System.out
                                .print("                CompletedProcessingDate");
                        System.out.println();
                        System.out.print("                    "
                                + feedSubmissionInfo
                                .getCompletedProcessingDate());
                        System.out.println();
                    }
                }
            }
            if (response.isSetResponseMetadata()) {
                System.out.print("        ResponseMetadata");
                System.out.println();
                ResponseMetadata responseMetadata = response
                        .getResponseMetadata();
                if (responseMetadata.isSetRequestId()) {
                    System.out.print("            RequestId");
                    System.out.println();
                    System.out.print("                "
                            + responseMetadata.getRequestId());
                    System.out.println();
                }
            }
            System.out.println(response.getResponseHeaderMetadata());
            System.out.println();
            System.out.println();

        } catch (MarketplaceWebServiceException ex) {

            System.out.println("Caught Exception: " + ex.getMessage());
            log.error("", ex);
            System.out.println("Response Status Code: " + ex.getStatusCode());
            System.out.println("Error Code: " + ex.getErrorCode());
            System.out.println("Error Type: " + ex.getErrorType());
            System.out.println("Request ID: " + ex.getRequestId());
            System.out.print("XML: " + ex.getXML());
            System.out.println("ResponseHeaderMetadata: " + ex.getResponseHeaderMetadata());
        }
    }

}
