/**
Copyright (C) Starnet [2020] - [2030] All Rights Reserved.
@author Felipe Alafy
Created on 20/11/2020
 */
package starnet.cdc.backend

import starnet.cdc.database.bean.bairro
import starnet.cdc.database.bean.cidade
import starnet.cdc.database.bean.clientes
import starnet.cdc.database.bean.contaLogada
import java.awt.Stroke

class validacao {
    fun validarNome(nome:String): Boolean {
        val cs:CharSequence = nome
        val pattern = Regex("[a-zA-Z-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ]{3,50}")
        if (nome.isNotEmpty()) {
            return pattern.containsMatchIn(cs)
        } else {
            return false
        }
    }
    fun validarDocumento(doc:String): Boolean {
        val cs:CharSequence = doc
        val pattern = Regex("[6][6][0-9]{5}")
        if (cs.isNotEmpty()) {
            return pattern.containsMatchIn(cs)
        } else {
            return false
        }
    }
    fun validarVencimento(venc:String):Boolean{
        if(!venc.isEmpty() && venc.length == 10){
            val pattern = Regex("[0-9][0-9]/[0-9][1-9]/[0-9][0-9][0-9][0-9]") //Regular Expression to validate a date
            return pattern.containsMatchIn(venc)
        } else {
            return false
        }
    }
    fun validarObs(obs:String) = obs.length <= 60

    fun validarLogin(conta: contaLogada):Boolean{
        var checkNome: Boolean
        var checkSenha: Boolean
        var cs:CharSequence

        //Nome
        cs = conta.login.toString()
        var pattern = Regex("[a-zA-Z-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ]{3,50}") //Regular Expression to validate a name
        checkNome = pattern.containsMatchIn(cs)

        //Senha
        cs = conta.pass.toString()
        pattern = Regex("[a-zA-Z]([a-zA-Z]|[0-9]|){7,32}") //Regular Expression to validate a password
        checkSenha = pattern.containsMatchIn(cs)

        return checkNome && checkSenha
    }

    //validar Nome para uma nova cidade
    fun validarCidade(cidade: cidade):Boolean{
        val nome:CharSequence = cidade.nome.toString()
        val pattern = Regex("[a-zA-Z-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ]|[\\s]")
        return pattern.containsMatchIn(nome)
    }

    fun validarBairro(bairro:bairro):Boolean{
        val nome:CharSequence = bairro.nome.toString()
        val pattern = Regex("[a-zA-Z-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ]|[\\s]")
        return pattern.containsMatchIn(nome)
    }

    fun validarCPF(cpf:String):Boolean{
        val CPF:CharSequence = cpf
        val pattern = Regex("^([0-9]{3}[.]){2}[0-9]{3}[-][0-9]{2}\$")
        return pattern.containsMatchIn(CPF)
    }

    fun check(cliente: clientes):Boolean {
        val errorClass = Error()
        var check = false
        check = validarNome(cliente.nome!!)
        if (!check){
            errorClass.openError("nome")
            return false
        }

        check = validarDocumento(cliente.documento!!)
        if (!check){
            errorClass.openError("documento")
            return false
        }

        check = validarVencimento(cliente.vencimento!!)
        if (!check){
            errorClass.openError("vencimento")
            return false
        }

        if (cliente.observacao == null) {
            return true
        } else {
            check = validarObs(cliente.observacao!!)
            if (!check){
                errorClass.openError("observação")
                return false
            }
        }
        return true
    }
}