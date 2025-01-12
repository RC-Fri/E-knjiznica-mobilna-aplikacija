import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.e_knjiznica_mobilna_aplikacija.R
import com.example.e_knjiznica_mobilna_aplikacija.data.model.Material
import kotlinx.coroutines.CoroutineScope
import java.sql.DriverManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

object DatabaseClient {
    private const val CONNECTION_URL = "jdbc:jtds:sqlserver://eknjiznica-db.database.windows.net:1433/E-knjiznica;ssl=require;encrypt=true;trustServerCertificate=true;sslProtocol=TLSv1.2;user=eknjiznica-sa;password=yourStrong(!)Password"

    private var connection: Connection? = null

    suspend fun initializeConnection(): Boolean {
        CoroutineScope(Dispatchers.IO).launch {
            try {
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

    suspend fun validateCredentials(username: String, password: String): Int {
        return withContext(Dispatchers.IO) {
            if (connection == null) {
                Log.e("DatabaseClient", "Database connection is not initialized!")
                return@withContext -1 // Return -1 to indicate failure
            }
            try {
                val callableStatement = connection!!.prepareCall("{CALL Get_id_from_login_username(?, ?)}")
                callableStatement.setString(1, username)
                callableStatement.setString(2, password)

                val resultSet: ResultSet = callableStatement.executeQuery()

                if (resultSet.next()) {
                    val userId = resultSet.getInt(1)
                    Log.d("DatabaseClient", "Login successful for user: $username with ID: $userId")
                    userId
                } else {
                    Log.e("DatabaseClient", "Invalid login for user: $username")
                    -1 // Return -1 to indicate invalid credentials
                }
            } catch (e: Exception) {
                Log.e("DatabaseClient", "Error during login validation: ${e.message}")
                -1 // Return -1 to indicate an error
            }
        }
    }

    suspend fun getBorrowedMaterials(userId: Int): List<Material> {
        return withContext(Dispatchers.IO) {
            val materials = mutableListOf<Material>()

            if (connection == null) {
                Log.e("DatabaseClient", "Database connection is not initialized!")
                return@withContext materials
            }

            try {
                val callableStatement = connection!!.prepareCall("{CALL GetBorrowedMaterials(?)}")
                callableStatement.setInt(1, userId)

                val resultSet: ResultSet = callableStatement.executeQuery()

                while (resultSet.next()) {
                    val id = resultSet.getInt("MaterialId")
                    val name = resultSet.getString("Name")
                    val status = resultSet.getString("Status")
                    //TODO: add to db val details = resultSet.getString("ReturnBy")
                    val details = "2025-02-15"
                    val material = Material(id, name, status, R.drawable.ic_launcher_foreground, details)
                    materials.add(material)
                }
                Log.d("DatabaseClient", "Retrieved ${materials.size} materials for user ID: $userId")
            } catch (e: Exception) {
                Log.e("DatabaseClient", "Error retrieving borrowed materials: ${e.message}")
            }
            materials
        }
    }

    suspend fun getMaterials(): List<Material> {
        return withContext(Dispatchers.IO) {
            val materials = mutableListOf<Material>()

            if (connection == null) {
                Log.e("DatabaseClient", "Database connection is not initialized!")
                return@withContext materials
            }

            try {
                val callableStatement = connection!!.prepareCall("{CALL GetMaterials()}")
                val resultSet: ResultSet = callableStatement.executeQuery()

                //Arbitrary chosen limitation on search
                var i = 0
                while (i<6 && resultSet.next()) {
                    val id = resultSet.getInt("MaterialId")
                    val name = resultSet.getString("Name")
                    val status = resultSet.getString("Status")
                    //val details = resultSet.getString("Details") Baza na žalost teh podatkov nima (Description preko api
                    val material = Material(id, name, status, R.drawable.ic_launcher_foreground, "None given")
                    materials.add(material)
                    i++
                }
                Log.d("DatabaseClient", "Retrieved ${materials.size} able-to-be borrowed materials for browsing")
            } catch (e: Exception) {
                Log.e("DatabaseClient", "Error retrieving borrowed materials: ${e.message}")
            }

            materials
        }
    }



    suspend fun extendMaterialDate(materialId: Int, userId: Int) {
        withContext(Dispatchers.IO) {
            if (connection == null) {
                Log.e("DatabaseClient", "Database connection is not initialized!")
                return@withContext
            }

            try {
                val callableStatement = connection!!.prepareCall("{CALL ExtendMaterialDate(?, ?)}")
                callableStatement.setInt(1, materialId)
                callableStatement.setInt(2, userId)

                callableStatement.execute()
                Log.d("DatabaseClient", "Successfully extended date for material ID: $materialId by user ID: $userId")
            } catch (e: Exception) {
                Log.e("DatabaseClient", "Error extending material date: ${e.message}")
            }
        }
    }

    suspend fun borrowMaterial(materialId: Int, userId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            if (connection == null) {
                Log.e("DatabaseClient", "Database connection is not initialized!")
                return@withContext false
            }

            try {
                val callableStatement = connection!!.prepareCall("{CALL BorrowMaterial(?, ?)}")
                callableStatement.setInt(1, materialId)
                callableStatement.setInt(2, userId)

                val rowsAffected = callableStatement.executeUpdate()
                if (rowsAffected > 0) {
                    Log.d("DatabaseClient", "Material ID: $materialId successfully borrowed by user ID: $userId")
                    return@withContext true
                } else {
                    Log.e("DatabaseClient", "Failed to borrow material ID: $materialId by user ID: $userId")
                    return@withContext false
                }
            } catch (e: Exception) {
                Log.e("DatabaseClient", "Error borrowing material: ${e.message}")
                return@withContext false
            }
        }
    }

    suspend fun searchMaterials(
        materialName: String,
        authorName: String,
        releaseDate: String,
        materialType: String
    ): List<Material> {
        return withContext(Dispatchers.IO) {
            val materials = mutableListOf<Material>()

            if (connection == null) {
                Log.e("DatabaseClient", "Database connection is not initialized!")
                return@withContext materials
            }

            try {
                val callableStatement = connection!!.prepareCall("{CALL SearchMaterials(?, ?, ?, ?)}")
                callableStatement.setString(1, materialName)
                callableStatement.setString(2, authorName)
                callableStatement.setString(3, releaseDate)
                callableStatement.setString(4, materialType)

                val resultSet: ResultSet = callableStatement.executeQuery()

                while (resultSet.next()) {
                    val id = resultSet.getInt("MaterialId")
                    val name = resultSet.getString("Name")
                    val status = resultSet.getString("Status")
                    val material = Material(id, name, status, R.drawable.ic_launcher_foreground, "None given")
                    materials.add(material)
                }

                Log.d("DatabaseClient", "Retrieved ${materials.size} materials matching query")
            } catch (e: Exception) {
                Log.e("DatabaseClient", "Error searching materials: ${e.message}")
            }

            materials
        }
    }



    //This but for required
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
