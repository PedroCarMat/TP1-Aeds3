
package entidades.cursos;

import aed3.Console;
import java.util.Scanner;

public class ControleCurso {

    ArquivoCurso arqCursos;
    Scanner console;

    public ControleCurso() throws Exception {
        arqCursos = new ArquivoCurso();
        console = new Scanner(System.in);
    }

    public void menuCursos(int userId) throws Exception {
        VisaoCurso v = new VisaoCurso();
        do {
            Console.clear();
            System.out.println("\nEntrePares 1.0");
            System.out.println("--------------");
            System.out.println("\n> Inicio > Meus Cursos\n");

            Curso[] cursos = arqCursos.readUsuario(userId);
            for (int i = 0; i < cursos.length; i++) {
                System.out.println("(" + (i+1) + ") " + cursos[i].getNome() + " - " + cursos[i].getDataInicio());
            }

            System.out.println("\n(A) Novo curso");
            System.out.println("(R) Retornar ao menu anterior");
            System.out.print("\nOpcao: ");
            String op = console.nextLine();
            if(op.length()==0) continue;
            char ch = op.charAt(0);
            if(ch=='A' || ch=='a') {
                Curso novo = v.leCurso(console, userId);
                if(novo!=null) {
                    arqCursos.create(novo);
                    System.out.println("Curso criado! Codigo: " + novo.getCodigoCompartilhavel());
                }
            }
            else if(ch=='R' || ch=='r') {
                break;
            }
            else {
                try {
                    int sel = Integer.parseInt(op);
                    if(sel>=1 && sel<=cursos.length) {
                        gerenciarCurso(cursos[sel-1]);
                    } else {
                        System.out.println("Opcao invalida");
                    }
                } catch(NumberFormatException e) {
                    System.out.println("Opcao invalida");
                }
            }
        } while(true);
    }

    private void gerenciarCurso(Curso c) throws Exception {
        VisaoCurso v = new VisaoCurso();
        do {
            Console.clear();
            System.out.println("\nEntrePares 1.0");
            System.out.println("--------------");
            System.out.println("\n> Inicio > Meus Cursos > " + c.getNome() + "\n");
            v.mostraCurso(c);
            if(c.getEstado()==0)
                System.out.println("Este curso esta aberto para inscricoes!\n");
            else
                System.out.println("Este curso nao permite novas inscricoes.\n");

            System.out.println("(A) Gerenciar inscritos no curso");
            System.out.println("(B) Corrigir dados do curso");
            System.out.println("(C) Encerrar inscricoes");
            System.out.println("(D) Concluir curso");
            System.out.println("(E) Cancelar curso");
            System.out.println("(F) Deletar curso");
            System.out.println("\n(R) Retornar ao menu anterior");
            System.out.print("\nOpcao: ");
            String op = console.nextLine();
            if(op.length()==0) continue;
            char ch = op.charAt(0);
            if(ch=='A' || ch=='a') {
                System.out.println("Operacao de gerenciamento de inscritos nao implementada neste TP1.");
            } else if(ch=='B' || ch=='b') {
                // corrigir dados
                System.out.println("Altere os dados. Deixe em branco para manter.");
                System.out.print("Nome: ");
                String nome = console.nextLine();
                if(nome.length()>0) c.setNome(nome);
                System.out.print("Data de inicio (dd/mm/aaaa): ");
                String data = console.nextLine();
                if(data.length()>0) {
                    try {
                        String[] p = data.split("/");
                        c.setDataInicio(java.time.LocalDate.of(Integer.parseInt(p[2]), Integer.parseInt(p[1]), Integer.parseInt(p[0])));
                    } catch(Exception e) {
                        System.out.println("Data invalida, mantendo valor anterior.");
                    }
                }
                System.out.print("Descricao: ");
                String desc = console.nextLine();
                if(desc.length()>0) c.setDescricao(desc);

                System.out.print("\nConfirma alteracao (S/N)? ");
                String conf = console.nextLine();
                if(conf.length()>0 && (conf.charAt(0)=='S' || conf.charAt(0)=='s')) {
                    if(arqCursos.update(c))
                        System.out.println("Curso atualizado!");
                    else
                        System.out.println("Erro ao atualizar curso");
                }

            } else if(ch=='C' || ch=='c') {
                c.setEstado((byte)1);
                arqCursos.update(c);
                System.out.println("Inscricoes encerradas para este curso.");
            } else if(ch=='D' || ch=='d') {
                c.setEstado((byte)2);
                arqCursos.update(c);
                System.out.println("Curso marcado como concluido.");
            } else if(ch=='E' || ch=='e') {
                c.setEstado((byte)3);
                arqCursos.update(c);
                System.out.println("Curso marcado como cancelado.");
            } else if(ch=='F' || ch=='f') {
                System.out.print("Confirma exclusao do curso (S/N)? ");
                String confEx = console.nextLine();
                if(confEx.length()>0 && (confEx.charAt(0)=='S' || confEx.charAt(0)=='s')) {
                    boolean ok = arqCursos.delete(c.getID());
                    if(ok) {
                        System.out.println("Curso excluido!");
                        return;
                    } else {
                        System.out.println("Erro na exclusao do curso!");
                    }
                }
            } else if(ch=='R' || ch=='r') {
                break;
            } else {
                System.out.println("Opcao invalida");
            }

            // reload course from storage in case of updates
            c = arqCursos.read(c.getID());
        } while(true);
    }

    public void close() throws Exception {
        arqCursos.close();
    }

}
