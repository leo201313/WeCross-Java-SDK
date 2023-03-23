package com.webank.wecrosssdk.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webank.wecrosssdk.rpc.common.ChainErrorMessage;
import com.webank.wecrosssdk.rpc.common.ResourceDetail;
import com.webank.wecrosssdk.rpc.common.Resources;
import com.webank.wecrosssdk.rpc.common.UAReceipt;
import com.webank.wecrosssdk.rpc.common.account.BCOSAccount;
import com.webank.wecrosssdk.rpc.common.account.ChainAccount;
import com.webank.wecrosssdk.rpc.common.account.FabricAccount;
import com.webank.wecrosssdk.rpc.common.account.UniversalAccount;
import com.webank.wecrosssdk.rpc.methods.Request;
import com.webank.wecrosssdk.rpc.methods.Response;
import com.webank.wecrosssdk.rpc.methods.response.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AccountTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public AccountResponse handleListAccounts() {
        AccountResponse response = new AccountResponse();
        UniversalAccount account = new UniversalAccount("hello", "world");
        List<ChainAccount> list = new ArrayList<>();
        ChainAccount bcosAccount = new BCOSAccount(1, "BCOS2.0", "XXX", "XXX", "address", true);
        list.add(bcosAccount);
        ChainAccount fabricAccount =
                new FabricAccount(2, "Fabric1.4", true, "xxx", "xxx", "membershipID");
        ChainAccount fabricAccount2 =
                new FabricAccount(3, "Fabric2.0", true, "xxx", "xxx", "membershipID");
        list.add(fabricAccount);
        list.add(fabricAccount2);
        account.setChainAccounts(list);
        response.setErrorCode(0);
        response.setAccount(account);
        return response;
    }

    public UAResponse backUAResponse(){
        UAReceipt receipt = new UAReceipt(0,"unknown","xxx");
        UniversalAccount account = new UniversalAccount("hello", "world");
        List<ChainAccount> list = new ArrayList<>();
        ChainAccount bcosAccount = new BCOSAccount(1, "BCOS2.0", "XXX", "XXX", "address", true);
        list.add(bcosAccount);
        ChainAccount fabricAccount =
                new FabricAccount(2, "Fabric1.4", true, "xxx", "xxx", "membershipID");
        ChainAccount fabricAccount2 =
                new FabricAccount(3, "Fabric2.0", true, "xxx", "xxx", "membershipID");
        list.add(fabricAccount);
        list.add(fabricAccount2);
        account.setChainAccounts(list);
        receipt.setUniversalAccount(account);

        UAResponse uaResponse = new UAResponse();
        uaResponse.setUAReceipt(receipt);
        uaResponse.setErrorCode(0);
        uaResponse.setMessage("unununknown");

        return uaResponse;
    }
    @Test
    public void printResponseTest() throws JsonProcessingException {
        System.out.println(handleListAccounts());
        System.out.println(objectMapper.writeValueAsString(handleListAccounts()));
    }

    @Test
    public void printUAResponseTest() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(backUAResponse()));
    }

    @Test
    public void parseUAResponse() throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<UAResponse> responseFuture = new CompletableFuture<>();
        String content = "{\n" +
                "  \"errorCode\": 0,\n" +
                "  \"message\": \"unununknown\",\n" +
                "  \"data\": {\n" +
                "    \"errorCode\": 0,\n" +
                "    \"message\": \"unknown\",\n" +
                "    \"credential\": \"xxx\",\n" +
                "    \"universalAccount\": {\n" +
                "      \"username\": \"hello\",\n" +
                "      \"password\": \"world\",\n" +
                "      \"chainAccounts\": [\n" +
                "        {\n" +
                "          \"keyID\": 1,\n" +
                "          \"type\": \"BCOS2.0\",\n" +
                "          \"isDefault\": true,\n" +
                "          \"pubKey\": \"XXX\",\n" +
                "          \"secKey\": \"XXX\",\n" +
                "          \"ext\": \"address\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"keyID\": 2,\n" +
                "          \"type\": \"Fabric1.4\",\n" +
                "          \"isDefault\": true,\n" +
                "          \"pubKey\": \"xxx\",\n" +
                "          \"secKey\": \"xxx\",\n" +
                "          \"ext\": \"membershipID\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"keyID\": 3,\n" +
                "          \"type\": \"Fabric2.0\",\n" +
                "          \"isDefault\": true,\n" +
                "          \"pubKey\": \"xxx\",\n" +
                "          \"secKey\": \"xxx\",\n" +
                "          \"ext\": \"membershipID\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";
        UAResponse response =
                objectMapper.readValue(content,UAResponse.class);
        responseFuture.complete(response);
        UAResponse responsesss = responseFuture.get(20, TimeUnit.SECONDS);
        System.out.println(objectMapper.writeValueAsString(responsesss));
    }

    public XAResponse handleStartTransaction() {
        XAResponse response = new XAResponse();
        response.setErrorCode(0);
        ChainErrorMessage err1 = new ChainErrorMessage("test/test1","test fail");
        ChainErrorMessage err2 = new ChainErrorMessage("test/test2","test fail");
        List<ChainErrorMessage> errList = new ArrayList<>();
        errList.add(err1);
        errList.add(err2);
        RawXAResponse rawResp = new RawXAResponse();
        rawResp.setChainErrorMessages(errList);
        response.setXARawResponse(rawResp);
        return response;
    }

    @Test
    public void printXAResponseTest() throws JsonProcessingException {
        System.out.println(handleStartTransaction());
        System.out.println(objectMapper.writeValueAsString(handleStartTransaction()));
    }

    public ResourceResponse handleListResources() {
        ResourceResponse response = new ResourceResponse();
        Resources resources = new Resources();
        ResourceDetail resourceDetail1 = new ResourceDetail();
        Map<Object, Object> properties = new HashMap<>();
        properties.put("property1","unknown");
        properties.put("property2",0);
        resourceDetail1.setProperties(properties);
        resourceDetail1.setDistance(2);
        resourceDetail1.setPath("test.test.test");
        resourceDetail1.setStubType("testType");


        ResourceDetail[] resourceDetails = new ResourceDetail[1];
        resourceDetails[0] = resourceDetail1;
        resources.setResourceDetails(resourceDetails);
        response.setErrorCode(0);
        response.setResources(resources);
        return response;
    }

    @Test
    public void printResourceResponseTest() throws JsonProcessingException {
        System.out.println(handleListResources());
        System.out.println(objectMapper.writeValueAsString(handleListResources()));
    }


}
