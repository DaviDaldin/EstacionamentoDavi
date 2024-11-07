package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Veiculo {
    private Scanner sc;
    public List<CadastroVeiculo> listaVeiculo = new ArrayList<>();
    private Vagas vagas;

    public Veiculo(Scanner sc, Vagas vagas) {
        this.sc = sc;
        this.vagas = vagas;
    }

    public void cadastroVeiculo() {
        System.out.print("Digite o número de veículos que deseja cadastrar: ");
        int qtd = sc.nextInt();

        for (int i = 0; i < qtd; i++) {
            System.out.print("Digite a placa do veículo: ");
            String placa = sc.next();
            System.out.print("Digite o modelo do veículo: ");
            String modelo = sc.next();

            System.out.print("Digite a hora de entrada do veículo (formato HH:mm): ");
            String horaEntrada = sc.next();
            while (!horaEntrada.matches("\\d{2}:\\d{2}")) {
                System.out.print("Formato inválido. Digite a hora de entrada no formato HH:mm: ");
                horaEntrada = sc.next();
            }

            System.out.print("Digite o número da vaga reservada: ");
            int numVaga = sc.nextInt();

            System.out.println("Selecione o tamanho do veículo:\n" +
                    "1- Pequeno\n" +
                    "2- Médio\n" +
                    "3- Grande");
            String tamanho = selecionarTamanho(sc.next());

            if (vagas.isVagaDisponivel(numVaga, tamanho)) {
                CadastroVeiculo veiculo = new CadastroVeiculo(placa, modelo, horaEntrada, numVaga, tamanho);
                listaVeiculo.add(veiculo);
                vagas.atualizarDisponibilidade(numVaga, false);
                System.out.println("Veículo cadastrado com sucesso!");
            } else {
                System.out.println("Vaga não disponível ou incompatível com o tamanho do veículo.");
            }
        }
    }

    private String selecionarTamanho(String opcao) {
        switch (opcao) {
            case "1": return "Pequeno";
            case "2": return "Médio";
            case "3": return "Grande";
            default: return "Pequeno";
        }
    }

    public void retirarVeiculo() {
        System.out.print("Digite o número da vaga do veículo a ser retirado: ");
        int numVaga = sc.nextInt();

        CadastroVeiculo veiculoParaRemover = null;

        for (CadastroVeiculo veiculo : listaVeiculo) {
            if (veiculo.numVaga == numVaga) {
                veiculoParaRemover = veiculo;
                break;
            }
        }

        if (veiculoParaRemover != null) {
            String[] horaEntradaSplit = veiculoParaRemover.horaEntrada.split(":");
            int horaEntrada = Integer.parseInt(horaEntradaSplit[0]) * 60 + Integer.parseInt(horaEntradaSplit[1]);
            int horaSaida = calcularHoraSaida();

            int tempoPermanencia = horaSaida - horaEntrada;
            double valorTotal = calcularValor(tempoPermanencia);
            listaVeiculo.remove(veiculoParaRemover);
            vagas.atualizarDisponibilidade(numVaga, true);
            System.out.println("Veículo de placa " + veiculoParaRemover.placa + " removido com sucesso!");
            System.out.println("Tempo de permanência: " + tempoPermanencia + " minutos. Valor a pagar: R$" + valorTotal);
        } else {
            System.out.println("Veículo não encontrado na vaga informada.");
        }
    }

    private int calcularHoraSaida() {
        System.out.print("Digite a hora de saída do veículo (formato HH:mm): ");
        String horaSaida = sc.next();
        while (!horaSaida.matches("\\d{2}:\\d{2}")) {
            System.out.print("Formato inválido. Digite a hora de saída no formato HH:mm: ");
            horaSaida = sc.next();
        }
        String[] horaSaidaSplit = horaSaida.split(":");
        return Integer.parseInt(horaSaidaSplit[0]) * 60 + Integer.parseInt(horaSaidaSplit[1]);
    }

    private double calcularValor(int tempo) {
        if (tempo <= 60) {
            return 5.0;
        } else if (tempo <= 180) {
            return 10.0;
        } else {
            return 15.0;
        }
    }

    public class CadastroVeiculo {
        private String placa;
        private String modelo;
        private String horaEntrada;
        private int numVaga;
        private String tamanho;

        public CadastroVeiculo(String placa, String modelo, String horaEntrada, int numVaga, String tamanho) {
            this.placa = placa;
            this.modelo = modelo;
            this.horaEntrada = horaEntrada;
            this.numVaga = numVaga;
            this.tamanho = tamanho;
        }

        @Override
        public String toString() {
            return "Veículo [Placa = " + placa +
                    ", Modelo = " + modelo +
                    ", Hora de Entrada = " + horaEntrada +
                    ", Vaga = " + numVaga +
                    ", Tamanho = " + tamanho + "]"; 
        }
    }
}
