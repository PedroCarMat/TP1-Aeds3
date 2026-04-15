import java.util.Scanner;

import aed3.Console;
import entidades.usuarios.ControleUsuario;
import entidades.cursos.ControleCurso;

public class Principal {
    
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);

        ControleUsuario controleUsuario = null;
        ControleCurso controleCurso = null;
        try {
            controleUsuario = new ControleUsuario();
            controleCurso = new ControleCurso();

            do {
                Console.clear();
                System.out.println("\nEntrePares 1.0");
                System.out.println("--------------");
                System.out.println("\n(A) Login");
                System.out.println("(B) Novo usuario");
                System.out.println("(C) Recuperar senha");
                System.out.println("\n(S) Sair");

                System.out.print("\nOpcao: ");
                String input = console.nextLine();
                if(input.length()==0) continue;
                char ch = input.charAt(0);

                switch(ch) {
                    case 'A': case 'a':
                        int userId = controleUsuario.login();
                        if(userId>0) {
                            System.out.println("Login realizado. Bem-vindo!");
                            // menu após login
                            boolean sairLogado = false;
                            while(!sairLogado) {
                                Console.clear();
                                System.out.println("\nEntrePares 1.0");
                                System.out.println("-----------------");
                                System.out.println("> Inicio\n");
                                System.out.println("(A) Meus dados");
                                System.out.println("(B) Meus cursos");
                                System.out.println("(C) Minhas inscricoes");
                                System.out.println("\n(S) Sair");
                                System.out.print("\nOpcao: ");
                                String in2 = console.nextLine();
                                if(in2.length()==0) continue;
                                char ch2 = in2.charAt(0);
                                if(ch2=='A' || ch2=='a') {
                                    boolean keep = controleUsuario.meusDados(userId);
                                    if(!keep) sairLogado = true;
                                }
                                else if(ch2=='B' || ch2=='b') controleCurso.menuCursos(userId);
                                else if(ch2=='C' || ch2=='c') System.out.println("Funcao de inscricoes sera implementada no TP2.");
                                else if(ch2=='S' || ch2=='s') sairLogado = true;
                                else System.out.println("Opcao invalida");
                            }
                        } else {
                            System.out.println("Login invalido!");
                        }
                        break;
                    case 'B': case 'b':
                        controleUsuario.cadastrar();
                        break;
                    case 'C': case 'c':
                        controleUsuario.recuperarSenha();
                        break;
                    case 'S': case 's':
                        System.out.println("Saindo...");
                        try { controleUsuario.close(); } catch(Exception e) {}
                        try { controleCurso.close(); } catch(Exception e) {}
                        console.close();
                        return;
                    default:
                        System.out.println("Opcao invalida");
                }
            } while(true);

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try { if(controleUsuario!=null) controleUsuario.close(); } catch(Exception e) {}
            try { if(controleCurso!=null) controleCurso.close(); } catch(Exception e) {}
            console.close();
        }
    }

}
