package fi.cafetsumppi.app;

/**
 * Created by Jupe Danger on 19.2.2018.
 */

public class oldCode {


/*
    private static class FetchFacebook extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url("https://graph.facebook.com/oauth/access_token?client_id=192900914634989&client_secret=285aaa7579b446d0c1b3e0779a886caa&grant_type=client_credentials");
                Request request = builder.build();
                okhttp3.Response response = client.newCall(request).execute();

                String accessToken = new JSONObject(response.body().string()).getString("access_token");

                client = new OkHttpClient();
                builder = new Request.Builder();
                builder.url("https://graph.facebook.com/sopiskelijapalvelut?access_token=" + accessToken);
                request = builder.build();
                response = client.newCall(request).execute();

                String pageid = new JSONObject(response.body().string()).getString("id");

                client = new OkHttpClient();
                builder = new Request.Builder();
                builder.url("https://graph.facebook.com/" + pageid + "/feed?access_token=" + URLEncoder.encode(accessToken, "UTF-8"));
                request = builder.build();
                response = client.newCall(request).execute();

                JSONObject jsonObject = new JSONObject(response.body().string());

            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
        }
    }
*/

}
