/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TraductorVM_Asm;

/**
 *
 * @author luise
 */
public class TraductorVM {
    String[] lineasCodigo=null;
    public TraductorVM(String contenido){
        lineasCodigo=contenido.split("\n");
    }
    
    public String traducir(){
        String codigoAsm="";
        codigoAsm="@256\nD=A\n@0\nM=D\n";
        for (int i = 0; i < lineasCodigo.length; i++) {
            if(lineasCodigo[i].contains("push")){
                codigoAsm+=escribirPush(lineasCodigo[i])+"\n";
            }else if(lineasCodigo[i].contains("pop")){
                codigoAsm+=escribirPop(lineasCodigo[i])+"\n";
            } else {
                codigoAsm+=escribirOperacionAritmetica(lineasCodigo[i])+"\n";
            }
        }
        return codigoAsm;
    }
    public String escribirPush(String linea){
        String lineaAsm="";
        String[] partesInstruccion=linea.split(" ");
        if(partesInstruccion[1].trim().equals("constant")){
            lineaAsm="@"+partesInstruccion[2]+"\nD=A\n@0\nA=M\nM=D\n";
            lineaAsm+=ActPuntero();
        }
        else if(partesInstruccion[1].trim().equals("static")){
            lineaAsm="@"+partesInstruccion[2]+"\nD=A\n@16\nA=A+D\nD=M\n@0\nA=M\nM=D\n";
            lineaAsm+=ActPuntero();
        }
        else if(partesInstruccion[1].trim().equals("local")||partesInstruccion[1].trim().equals("argument")||partesInstruccion[1].trim().equals("this")||partesInstruccion[1].trim().equals("that")){
            String pos="";
            if(partesInstruccion[1].trim().equals("local")){
                pos="1";
            }else if(partesInstruccion[1].trim().equals("argument")){
                pos="2";
            }else if(partesInstruccion[1].trim().equals("this")){
                pos="3";
            }else if(partesInstruccion[1].trim().equals("that")){
                pos="4";
            }
            lineaAsm="@"+partesInstruccion[2]+"\nD=A\n@"+pos+"\nA=M+D\nD=M\n@0\nA=M\nM=D\n";
            lineaAsm+=ActPuntero();
        }
        else if(partesInstruccion[1].trim().equals("temp")){
            lineaAsm="@"+partesInstruccion[2]+"\nD=A\n@5\nA=A+D\nD=M\n@0\nA=M\nM=D\n";
            lineaAsm+=ActPuntero();
        }
        return lineaAsm;
    }
    public String escribirPop(String linea){
         String lineaAsm="";
        String[] partesInstruccion=linea.split(" ");
        if(partesInstruccion[1].trim().equals("static")){
            lineaAsm="@"+partesInstruccion[2]+"\nD=A\n@16\n@D=A+D\n@15\nM=D\n@0\nAM=M-1\nD=M\n@15\nA=M\nM=D";
        }else if(partesInstruccion[1].trim().equals("local")||partesInstruccion[1].trim().equals("argument")||partesInstruccion[1].trim().equals("this")||partesInstruccion[1].trim().equals("that")){
            String pos="";
            if(partesInstruccion[1].trim().equals("local")){
                pos="1";
            }else if(partesInstruccion[1].trim().equals("argument")){
                pos="2";
            }else if(partesInstruccion[1].trim().equals("this")){
                pos="3";
            }else if(partesInstruccion[1].trim().equals("that")){
                pos="4";
            }
            lineaAsm="@"+partesInstruccion[2]+"\nD=A\n@"+pos+"\nD=M+D\n@15\nM=D\n@0\nAM=M-1\nD=M\n@15\nA=M\nM=D";            
        }
        else if(partesInstruccion[1].trim().equals("temp")){
            lineaAsm="@"+partesInstruccion[2]+"\nD=A\n@5\nD=A+D\n@15\nM=D\n@0\nAM=M-1\nD=M\n@15\nA=M\nM=D";
        }
        return lineaAsm;
    }
    public String escribirOperacionAritmetica(String linea){
        String lineaAsm="";
        if(linea.trim().equals("add")){
            lineaAsm="@0\nAM=M-1\nD=M\n@0\nAM=M-1\nD=D+M\n@0\nM=D";
        }
        else if(linea.trim().equals("sub")){
            lineaAsm="@0\nAM=M-1\nD=M\n@0\nAM=M-1\nD=D-M\n@0\nM=D";
        }
        else if(linea.trim().equals("neg")){
            lineaAsm="@0\nAM=M-1\nD=!M\n@0\nM=D";
        }
        return lineaAsm;
    }
    public String ActPuntero(){
        String actpuntero="";
            actpuntero="@0\nM=M+1";    
        return actpuntero;
    }
    
}
