/*
 */

package com.example.jetnews.data.equityChangesStatement

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "equity_changes_statement_item")
data class EquityChangesStatementItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val equityChangesStatementItemId: Long,
    val equityChangesStatementId: Long,
    val type: String,

    //Capital Social Integralizado
    val paidInCapitalValue: Double,

    //Reservas de Capital, Opções Outorgadas e Acões em Tesouraria
    val capitalReservesGrantedOptionsAndTreasurySharesValue: Double,

    //Reservas de Lucros
    val profitReservesValue: Double,

    //Lucros ou Prejuízos Acumulados
    val accumulatedProfitsOrLossesValue: Double,

    //Outros Resultados Abrangentes
    val otherComprehensiveResultsValue: Double,

    //Patrimônio Líquido dos Sócios da Controladora
    val equityOfTheParentCompanyShareholdersValue: Double,

    //Participação dos Não Controladores no PL das Controlada
    val nonControllingInterestsInEquityOfSubsidiaryValue: Double,

    //Patrimônio Líquido Consolidado
    val consolidatedEquityValue: Double,

    val explanatoryNote: String,

    val deleted: Int,
    ) {

    override fun toString() = type
}
