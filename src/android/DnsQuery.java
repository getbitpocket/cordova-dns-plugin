package at.bitpocket;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.SRVRecord;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DnsQuery extends CordovaPlugin {

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false if not.
     */
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {

        try {
            String hostname = args.getString(0);

            if ("resolve".equals(action)) {
                String address  = InetAddress.getByName(hostname).getHostAddress();
                callbackContext.success(address);
            } else if ("resolveAll".equals(action)) {
                JSONArray addresses = new JSONArray();
                for (InetAddress address : InetAddress.getAllByName(hostname)) {
                    addresses.put(address.getHostAddress());
                }
                callbackContext.success(addresses);
            } else if ("srvLookup".equals(action)) {
                String srv = args.getString(0);
                hostname = args.getString(1);
                resolveSrv(srv, hostname, callbackContext);
            } else {
                callbackContext.error("invalid action");
            }
        } catch (UnknownHostException uhe) {
            callbackContext.error(uhe.getMessage());
        } catch (JSONException je) {
            callbackContext.error("Bad params: " + je.getMessage());
        }

        return false;
    }

    public static void resolveSrv(String srvPortion, String uri, CallbackContext callbackContext) {
        URL serviceURL;

        try {
            serviceURL = new URL("https://" + uri + ":443");
            String srvName = srvPortion + "." + serviceURL.getHost();
            Integer port;

            JSONArray addresses = new JSONArray();
            try {
                Lookup lookup = new Lookup(srvName, Type.SRV);

                Record[] records = lookup.run();

                if (records != null && records.length > 0) {
                    for (Record record: records) {
                        SRVRecord srv = (SRVRecord)records[0];
                        String hostname = srv.getTarget().toString().replaceFirst("\\.$", "");
                        port = srv.getPort();
                        serviceURL = new URL("https://" + hostname + ":" + port);
                        addresses.put(serviceURL.toString());
                    }
                } else {
                    addresses.put(serviceURL.toString());
                }
                callbackContext.success(addresses);

            } catch (TextParseException e) {
                e.printStackTrace();
                callbackContext.error(e.toString());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            callbackContext.error(e.toString());
        }

    }

}
