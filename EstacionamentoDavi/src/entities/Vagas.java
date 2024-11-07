package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vagas {
    private Scanner sc;
    private List<CadastroVagas> listaVagas = new ArrayList<>();
    private List<Boolean> disponibilidadeVagas = new ArrayList<>();

    public Vagas(Scanner sc) {
        this.sc = sc;
    }

    public void cadastroVagas() {
        System.out.println("Digite o número da vaga:");
        int num = sc.nextInt();
        sc.nextLine();

        System.out.println("Digite o tamanho da vaga (Pequeno/Médio/Grande):");
        String tamanho = sc.nextLine();

        System.out.println("A vaga está disponível? (Digite 'Livre' para disponível ou 'Ocupado' para indisponível):");
        String disponivel = sc.nextLine();

        CadastroVagas novaVaga = new CadastroVagas(num, tamanho, disponivel);
        listaVagas.add(novaVaga);
        disponibilidadeVagas.add(disponivel.equalsIgnoreCase("Livre"));

        System.out.println("Vaga cadastrada com sucesso!");
    }

    public boolean isVagaDisponivel(int num, String tamanhoVeiculo) {
        for (int i = 0; i < listaVagas.size(); i++) {
            CadastroVagas vaga = listaVagas.get(i);
            if (vaga.num == num && disponibilidadeVagas.get(i) && isTamanhoCompatível(vaga, tamanhoVeiculo)) {
                return true;
            }
        }
        return false;
    }

    public void excluirVagas() {
        System.out.print("Digite o número da vaga que deseja excluir: ");
        int num = sc.nextInt();

        CadastroVagas vagaParaRemover = null;

        for (CadastroVagas vaga : listaVagas) {
            if (vaga.num == num) {
                vagaParaRemover = vaga;
                break;
            }
        }

        if (vagaParaRemover != null) {
            listaVagas.remove(vagaParaRemover);
            System.out.println("Vaga " + num + " excluída com sucesso!");
        } else {
            System.out.println("Vaga não encontrada.");
        }
    }

    public void listarVagas() {
        System.out.println("Lista de Vagas:");
        for (CadastroVagas vaga : listaVagas) {
            System.out.println(vaga);
        }
    }

    public void atualizarDisponibilidade(int num, boolean disponivel) {
        for (int i = 0; i < listaVagas.size(); i++) {
            CadastroVagas vaga = listaVagas.get(i);
            if (vaga.num == num) {
                disponibilidadeVagas.set(i, disponivel);
                break;
            }
        }
    }

    private boolean isTamanhoCompatível(CadastroVagas vaga, String tamanhoVeiculo) {
        return vaga.tamanho.equalsIgnoreCase(tamanhoVeiculo);
    }

    
    public class CadastroVagas {
        private int num;
        private String tamanho;
        private String disponivel;

        public CadastroVagas(int num, String tamanho, String disponivel) {
            this.num = num;
            this.tamanho = tamanho;
            this.disponivel = disponivel;
        }

        @Override
        public String toString() {
            return "Vaga [Número = " + num +
                    ", Tamanho = " + tamanho +
                    ", Disponibilidade = " + disponivel + "]";
        }
    }
}
