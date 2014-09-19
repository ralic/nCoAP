/**
 * Copyright (c) 2012, Oliver Kleine, Institute of Telematics, University of Luebeck
 * All rights reserved
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *
 *  - Redistributions of source messageCode must retain the above copyright notice, this list of conditions and the following
 *    disclaimer.
 *
 *  - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 *  - Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or promote
 *    products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
///**
// * Copyright (c) 2012, Oliver Kleine, Institute of Telematics, University of Luebeck
// * All rights reserved
// *
// * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
// * following conditions are met:
// *
// *  - Redistributions of source code must retain the above copyright notice, this list of conditions and the following
// *    disclaimer.
// *
// *  - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
// *    following disclaimer in the documentation and/or other materials provided with the distribution.
// *
// *  - Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or promote
// *    products derived from this software without specific prior written permission.
// *
// * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
// * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
// * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
// * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
// * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
// * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//package de.uniluebeck.itm.ncoap.communication;
//
//import de.uniluebeck.itm.ncoap.application.client.CoapClientApplication;
//import de.uniluebeck.itm.ncoap.plugtest.client.CoapClientTestCallback;
//import de.uniluebeck.itm.ncoap.plugtest.server.CoapTestServer;
//import de.uniluebeck.itm.ncoap.plugtest.server.webservice.NotObservableTestWebService;
//import de.uniluebeck.itm.ncoap.message.CoapRequest;
//import de.uniluebeck.itm.ncoap.message.CoapResponse;
//import de.uniluebeck.itm.ncoap.message.MessageCode;
//import de.uniluebeck.itm.ncoap.message.MessageType;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//import org.junit.Test;
//
//import java.net.URI;
//import java.nio.charset.Charset;
//import java.util.Collections;
//import java.util.Set;
//import java.util.TreeSet;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
///**
// * Created with IntelliJ IDEA.
// * User: olli
// * Date: 18.06.13
// * Time: 14:58
// * To change this template use File | Settings | File Templates.
// */
//public class TestParallelRequests extends AbstractCoapCommunicationTest {
//
//    private static CoapClientApplication client;
//
//    private static final int NUMBER_OF_PARALLEL_REQUESTS = 100;
//
//    private static CoapClientTestCallback[] responseProcessors =
//            new CoapClientTestCallback[NUMBER_OF_PARALLEL_REQUESTS];
//
//    private static CoapRequest[] requests = new CoapRequest[NUMBER_OF_PARALLEL_REQUESTS];
//
//    private static CoapTestServer server;
//
//    @Override
//    public void setupLogging() throws Exception {
//        Logger.getLogger("de.uniluebeck.itm.ncoap.communication.TestParallelRequests").setLevel(Level.DEBUG);
//        Logger.getLogger("de.uniluebeck.itm.ncoap.plugtest.client.CoapClientTestCallback").setLevel(Level.DEBUG);
//    }
//
//    @Override
//    public void setupComponents() throws Exception {
//
//        server = new CoapTestServer(0);
//
//        //Add different webservices to server
//        for(int i = 0; i < NUMBER_OF_PARALLEL_REQUESTS; i++){
//            server.registerService(new NotObservableTestWebService("/service" + (i+1),
//                    "Status of Webservice " + (i+1), 0));
//        }
//
//        //Create client, callbacks and requests
//        client = new CoapClientApplication();
//
//        for(int i = 0; i < NUMBER_OF_PARALLEL_REQUESTS; i++){
//            responseProcessors[i] = new CoapClientTestCallback();
//            requests[i] =  new CoapRequest(MessageType.CON, MessageCode.GET,
//                    new URI("coap://localhost:" + server.getServerPort() + "/service" + (i+1)));
//        }
//    }
//
//    @Override
//    public void shutdownComponents() throws Exception {
//        client.shutdown();
//        server.shutdown();
//    }
//
//   @Override
//    public void createTestScenario() throws Exception {
//
//        for(int i = 0; i < NUMBER_OF_PARALLEL_REQUESTS; i++){
//            client.sendCoapRequest(requests[i], responseProcessors[i]);
//        }
//
//        //await responses
//        Thread.sleep(12000);
//    }
//
//    @Test
//    public void testClientsReceivedCorrectResponses(){
//        for (int i = 0; i < NUMBER_OF_PARALLEL_REQUESTS; i++){
//            CoapResponse coapResponse = responseProcessors[i].getCoapResponses().get(0);
//
//            assertEquals("Response Processor " + (i+1) + " received wrong message content",
//                    "Status of Webservice " + (i+1), coapResponse.getContent().toString(Charset.forName("UTF-8")));
//        }
//    }
//
//    @Test
//    public void serverReceivedAllRequests(){
//
//        Set<Integer> usedMessageIDs = Collections.synchronizedSet(new TreeSet<Integer>());
//        for(int i = 1; i <= NUMBER_OF_PARALLEL_REQUESTS; i++){
//            usedMessageIDs.add(i);
//        }
//
//        for(int messageID : server.getRequestReceptionTimes().keySet()){
//            usedMessageIDs.remove(messageID);
//        }
//
//        for(int messageID : usedMessageIDs){
//            log.info("Missing message ID: " + messageID);
//        }
//
//        assertEquals("Server did not receive all requests.",
//                NUMBER_OF_PARALLEL_REQUESTS, server.getRequestReceptionTimes().size());
//    }
//}
