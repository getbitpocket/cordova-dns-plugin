package at.bitpocket;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

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

}