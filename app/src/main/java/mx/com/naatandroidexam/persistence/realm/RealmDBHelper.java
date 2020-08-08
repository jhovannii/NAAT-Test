package mx.com.naatandroidexam.persistence.realm;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import mx.com.naatandroidexam.model.Elemento;

public class RealmDBHelper {
    private Realm realm;

    public RealmDBHelper() {
        realm = Realm.getDefaultInstance();
    }

    public void guardarElementosClaro(RealmList<Elemento> elementoRealmList) {
        realm.executeTransaction(realm -> {
            realm.where(Elemento.class).equalTo("nombre", "Claro").findAll().deleteAllFromRealm();
            realm.insert(elementoRealmList);
        });
    }

    public void guardarElementosTuenti(RealmList<Elemento> elementoRealmList) {
        realm.executeTransaction(realm -> {
            realm.where(Elemento.class).equalTo("nombre", "Tuenti").findAll().deleteAllFromRealm();
            realm.insert(elementoRealmList);
        });
    }

    public void borrarDatosRealm() {
        realm.executeTransaction(realm -> {
            realm.where(Elemento.class).findAll().deleteAllFromRealm();
            realm.close();
        });
    }

    public void guardarElementosEntel(RealmList<Elemento> elementoRealmList) {
        realm.executeTransaction(realm -> {
            realm.where(Elemento.class).equalTo("nombre", "Entel").findAll().deleteAllFromRealm();
            realm.insert(elementoRealmList);
        });
    }

    public RealmList<Elemento> getElementosClaro() {
        RealmResults<Elemento> lista = realm.where(Elemento.class).equalTo("nombre", "Claro").findAll();
        RealmList<Elemento> elementosClaro = new RealmList<>();
        elementosClaro.addAll(lista);
        return elementosClaro;
    }

    public RealmList<Elemento> getElementosTuenti() {
        RealmResults<Elemento> lista = realm.where(Elemento.class).equalTo("nombre", "Tuenti").findAll();
        RealmList<Elemento> elementosTuenti = new RealmList<>();
        elementosTuenti.addAll(lista);
        return elementosTuenti;
    }

    public RealmList<Elemento> getElementosEntel() {
        RealmResults<Elemento> lista = realm.where(Elemento.class).equalTo("nombre", "Entel").findAll();
        RealmList<Elemento> elementosEntel = new RealmList<>();
        elementosEntel.addAll(lista);
        return elementosEntel;
    }
}
