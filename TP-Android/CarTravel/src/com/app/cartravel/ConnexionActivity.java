package com.app.cartravel;

import java.io.IOException;
import java.net.URI;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.jsonparser.JsonUtilisateur;
import com.app.cartravel.utilitaire.Util;
import com.app.cartravel.utilitaire.UtilisateurDataSource;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class ConnexionActivity extends Activity {

	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "0.3";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	private EditText username;
	private EditText password;

	private HttpClient m_ClientHttp = new DefaultHttpClient();

	private GoogleCloudMessaging gcm;
	// private SharedPreferences prefs;
	private String regid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connexion);
		if (checkPlayServices()) {
			username = (EditText) this.findViewById(R.id.login_username);
			password = (EditText) this.findViewById(R.id.login_password);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		checkPlayServices();

		// Récupère la dernière personne à s'être connecté.
		UtilisateurDataSource dataSource = new UtilisateurDataSource(this);
		dataSource.open();
		Utilisateurs dernierConnecte = dataSource.getDernierConnecte();

		dataSource.close();
		if (dernierConnecte != null) {
			// Si la dernière personne à s'être connecté est encore
			// connectée, on ouvre l'application
			if (dernierConnecte.getEstConnecte() == 1) {
				Intent i = new Intent(this, MainActivity.class);
				this.startActivity(i);
				this.finish();
			} else {
				// Si la dernière personne à s'être connecté n'est plus
				// connectée, on met son courriel dans la barre de courriel
				((EditText) this.findViewById(R.id.login_username))
						.setText(dernierConnecte.getCourriel());
			}
		}
	}

	public void btnCreerCompte(View source) {
		Intent creationCompte = new Intent(this, CreationCompteActivity.class);
		this.startActivity(creationCompte);
	}

	public void btnConnexion(View source) {
		// VÃ©rifier si la combinaison usager/mot de passe existe sur le service
		// web
		String strCourriel = username.getText().toString();
		String strMotDePasse = password.getText().toString();

		if (Util.ValiderString(new String[] { strCourriel, strMotDePasse })) {
			new ConnexionTask(this).execute(strCourriel, strMotDePasse);
		}
	}

	private class ConnexionTask extends AsyncTask<String, Void, Utilisateurs> {
		private Exception m_Exp;
		private Context m_Context;

		public ConnexionTask(Context p_context) {
			this.m_Context = p_context;
		}

		@Override
		protected Utilisateurs doInBackground(String... params) {
			Utilisateurs m_Utilisateur = null;
			try {
				m_Utilisateur = new Utilisateurs();
				m_Utilisateur.setCourriel(params[0]);
				m_Utilisateur.setMotDePasse(Util.sha1(params[1]));
				URI uri = new URI("http", Util.WEB_SERVICE,
						Util.REST_UTILISATEUR + "/"
								+ m_Utilisateur.getCourriel()
								+ Util.REST_CONNEXION, "password="
								+ m_Utilisateur.getMotDePasse(), null);
				HttpGet getMethod = new HttpGet(uri);

				String body = m_ClientHttp.execute(getMethod,
						new BasicResponseHandler());
				Log.i("Reponse", body);
				Utilisateurs utilisateurConnecte = JsonUtilisateur
						.ToUtilisateur(body);
				m_Utilisateur.setPseudo(utilisateurConnecte.getPseudo());
				m_Utilisateur.setDateAjoutUser(utilisateurConnecte
						.getDateAjoutUser());
			} catch (Exception e) {
				m_Exp = e;
			}
			return m_Utilisateur;
		}

		@Override
		protected void onPostExecute(Utilisateurs result) {
			if (m_Exp == null && result != null) {
				UtilisateurDataSource dataSource = new UtilisateurDataSource(
						m_Context);
				result.setEstConnecte(1);
				dataSource.open();
				Utilisateurs utilisateurExistant = dataSource
						.getUtilisateur(result.getCourriel());
				if (utilisateurExistant == null) {
					// RÃ©cupÃ©rer l'usager sur le service web et l'ajouter en
					// local
					dataSource.insert(result);
				} else {
					result.setId(utilisateurExistant.getId());
					dataSource.update(result);
				}
				// Met Ã  jour le dernier utilisateur connectÃ©
				dataSource.modifierDernierConnecte(result);
				dataSource.close();

				if (checkPlayServices()) {
					gcm = GoogleCloudMessaging.getInstance(m_Context);
					regid = getRegistrationId(m_Context);

					if (regid.isEmpty()) {
						new RegisterInBackgroundTask(m_Context).execute();
					}
				} else {
					Log.i("Connexion",
							"No valid Google Play Services APK found.");
				}

				Intent i = new Intent(m_Context, MainActivity.class);
				m_Context.startActivity(i);
				((Activity) m_Context).finish();
			} else {
				Toast.makeText(
						m_Context,
						m_Context.getResources().getText(
								R.string.toast_erreur_connexion),
						Toast.LENGTH_SHORT).show();
				Log.i("CONNEXION", "Erreur : " + m_Exp.getMessage());
				password.setText(null);
			}
		}
	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If it
	 * doesn't, display a dialog that allows users to download the APK from the
	 * Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i("Connexion", "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 * 
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i("Connexion", "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i("Connexion", "App version changed.");
			return "";
		}
		return registrationId;
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences,
		// but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(ConnexionActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and app versionCode in the application's
	 * shared preferences.
	 */
	private class RegisterInBackgroundTask extends AsyncTask<Void, Void, Void> {
		private Context m_Context;

		public RegisterInBackgroundTask(Context p_Context) {
			this.m_Context = p_Context;
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				if (gcm == null) {
					gcm = GoogleCloudMessaging.getInstance(m_Context);
				}
				regid = gcm.register(Util.GOOGLE_SENDER_ID);

				// You should send the registration ID to your server over HTTP,
				// so it can use GCM/HTTP or CCS to send messages to your app.
				// The request to your server should be authenticated if your
				// app
				// is using accounts.
				new RegisterToBackEndTask(m_Context).execute();

				// For this demo: we don't need to send it because the device
				// will send upstream messages to a server that echo back the
				// message using the 'from' address in the message.

				// Persist the regID - no need to register again.
				storeRegistrationId(m_Context, regid);
			} catch (IOException ex) {
				// If there is an error, don't just keep trying to register.
				// Require the user to click a button again, or perform
				// exponential back-off.
			}
			return null;
		}

	}

	private class RegisterToBackEndTask extends AsyncTask<Void, Void, Void> {

		@SuppressWarnings("unused")
		private Context m_Context;

		public RegisterToBackEndTask(Context p_Context) {
			this.m_Context = p_Context;
		}

		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}
	}

	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 * 
	 * @param context
	 *            application's context.
	 * @param regId
	 *            registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i("Connexion", "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}
}
