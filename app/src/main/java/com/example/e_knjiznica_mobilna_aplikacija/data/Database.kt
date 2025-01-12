import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import java.sql.DriverManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

object DatabaseClient {
    @SuppressLint("AuthLeak")
    private const val CONNECTION_URLa =
        "jdbc:sqlserver://eknjiznica-db.database.windows.net:1433;" +
                "database=E-knjiznica;" +
                "user=eknjiznica-sa@eknjiznica-db;" +
                "password=yourStrong(!)Password;" +
                "encrypt=true;" +
                "trustServerCertificate=false;" +
                "hostNameInCertificate=*.database.windows.net;" +
                "loginTimeout=30;"

//Dbconstring here

    private var connection: Connection? = null

    suspend fun initializeConnection(): Boolean {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Load the jTDS SQL Server driver
                Class.forName("net.sourceforge.jtds.jdbc.Driver") // For jTDS
                connection = DriverManager.getConnection(CONNECTION_URL)


                val connection: Connection = DriverManager.getConnection(CONNECTION_URL)
                Log.d("DatabaseClient", "Connection successful!")

                // Create a statement and execute the query
                //For debug purposes
                val query = "SELECT * FROM Material"
                val statement: Statement = connection.createStatement()
                val resultSet: ResultSet = statement.executeQuery(query)

                Log.d("DatabaseClient", "Connection successful!")
            } catch (e: Exception) {
                Log.e("DatabaseClient", "Connection failed: ${e.message}")
            }
        }
        return true
    }

    //TODO("Actual validation based on clan username and password") props a int fun that returns id
    suspend fun validateCredentials(username: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {

            if (connection == null) {
                Log.e("DatabaseClient", "Database connection is not initialized!")
                return@withContext false
            }
            try {
                val statement = connection!!.prepareStatement(
                    "SELECT COUNT(*) FROM Users WHERE Email = ? AND Password = ?"
                )
                statement.setString(1, username)
                statement.setString(2, password)
                val resultSet: ResultSet = statement.executeQuery()

                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    Log.d("DatabaseClient", "Login successful for user: $username")
                    true
                } else {
                    Log.e("DatabaseClient", "Invalid login for user: $username")
                    false
                }
            } catch (e: Exception) {
                Log.e("DatabaseClient", "Error during login validation: ${e.message}")
                false
            }
            true
        }
    }

    fun insertGradivo(
        db: SQLiteDatabase,
        inventarnaStevilka: Int,
        idTipGradiva: Int,
        idZalozba: Int,
        idStatus: Int,
        idPodruznice: Int,
        naziv: String,
        datumIzdaje: String
    ) {
        val values = ContentValues().apply {
            put("Inventarna_stevilka", inventarnaStevilka)
            put("ID_tip_gradiva", idTipGradiva)
            put("ID_zalozba", idZalozba)
            put("ID_status", idStatus)
            put("ID_podruznice", idPodruznice)
            put("Naziv", naziv)
            put("Datum_izdaje", datumIzdaje)
        }
        db.insert("GRADIVO", null, values)
    }

    fun updateGradivo(
        db: SQLiteDatabase,
        inventarnaStevilka: Int,
        idTipGradiva: Int,
        idZalozba: Int,
        idStatus: Int,
        idPodruznice: Int,
        naziv: String,
        datumIzdaje: String
    ) {
        val values = ContentValues().apply {
            put("ID_tip_gradiva", idTipGradiva)
            put("ID_zalozba", idZalozba)
            put("ID_status", idStatus)
            put("ID_podruznice", idPodruznice)
            put("Naziv", naziv)
            put("Datum_izdaje", datumIzdaje)
        }
        val selection = "Inventarna_stevilka = ?"
        val selectionArgs = arrayOf(inventarnaStevilka.toString())
        db.update("GRADIVO", values, selection, selectionArgs)
    }

    fun deleteGradivo(db: SQLiteDatabase, inventarnaStevilka: Int) {
        val selection = "Inventarna_stevilka = ?"
        val selectionArgs = arrayOf(inventarnaStevilka.toString())
        db.delete("GRADIVO", selection, selectionArgs)
    }
}
