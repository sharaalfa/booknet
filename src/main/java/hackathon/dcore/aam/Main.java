package hackathon.dcore.aam;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.DCoreSdk;
import ch.decent.sdk.api.BroadcastApi;
import ch.decent.sdk.api.SubscriptionApi;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.*;
import ch.decent.sdk.net.TrustAllCerts;
import kotlin.Pair;
import okhttp3.OkHttpClient;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        OkHttpClient client = TrustAllCerts.wrap(new OkHttpClient().newBuilder()).build();
        DCoreApi api = DCoreSdk.create(
                client,
                "wss://hackathon2.decent.ch:8090",
                "https://hackathon2.decent.ch:8090",
                LoggerFactory.getLogger("SDK_SAMPLE"));


//        create user credentials
        Credentials credentials = api.getAccountApi()
                .createCredentials("dw-on77iq-n13cjxey2ne4-sgga2xfs7n", "5KMRpfRVkMdovjHuftQCHTbR1HPicKBzNEMUGWmZpfyYeJHBPHJ")
                .blockingGet();
        //dw-on77iq-n13cjxey2ne4-sgga2xfs7n dupion murkily bravade yearly upgo lob uptower foghorn rhyptic midway sketch sittine sai subsidy magenta forevow
        //5Jyh9rLzFvoZMfWJ3YiHCK9PE4khzDW43yexBUkGqUPdyXbGJLP
        //dw-on77iq-n13cjxey2ne4-sgga2xfs7n

//        balance
        Pair<Asset, AssetAmount> balance = api.getBalanceApi()
                .getBalanceWithAsset(credentials.getAccount(), "DCT")
                .blockingGet();
        System.out.println(("BALANCE"+ balance.getFirst().format(balance.getSecond().getAmount(), 2)));

//        transfer
        AssetAmount amount = balance.getFirst().amount(0.12345);
        TransactionConfirmation confirmation = api.getOperationsHelper()
                .transfer(credentials, "dw-h8nrlairbx1vkxmt97wft8jj4ri5f4", amount, "hello memo")
                .blockingGet();
        System.out.println(("TRANSACTION" + confirmation.toString()));

//        verify
        ProcessedTransaction trx = api.getTransactionApi().getTransaction(confirmation)
                .blockingGet();
        System.out.println(("TRANSACTION EXIST"+trx.toString()));

        ChainObject id = ChainObject.parse("1.2.357");

//        history
        ContentSubmitOperation op = new ContentSubmitOperation(2, id, Collections.emptyList(), "", Collections.emptyList(), 0, "", 0, Collections.emptyList(), 2018, 0, "json", "")
                api.getBroadcastApi().broadcastWithCallback("pti", op)
        List<OperationHistory> history = api.getHistoryApi().getAccountHistory(credentials.getAccount()).blockingGet();
        System.out.println("HISTORY"+history.get(0));




    }
}
