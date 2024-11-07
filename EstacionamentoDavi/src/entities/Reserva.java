package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reserva {
    private Scanner sc;
    private Vagas vagas;
    private List<ReservaVeiculo> listaReservas = new ArrayList<>();

    public Reserva(Scanner sc, Vagas vagas) {
        this.sc = sc;
        this.vagas = vagas;
    }

    public void novaReserva() {
        System.out.print("Digite a placa do veículo para reserva: ");
        String placa = sc.next();
        System.out.print("Digite o número da vaga para reserva: ");
        int numVaga = sc.nextInt();
        System.out.print("Digite a hora de entrada (formato HH:mm): ");
        String horaEntrada = sc.next();

        while (!horaEntrada.matches("\\d{2}:\\d{2}")) {
            System.out.print("Formato inválido. Digite a hora de entrada no formato HH:mm: ");
            horaEntrada = sc.next();
        }

        System.out.println("Selecione o tamanho do veículo:\n" +
                "1- Pequeno\n" +
                "2- Médio\n" +
                "3- Grande");
        String tamanho = selecionarTamanho(sc.next());

        if (vagas.isVagaDisponivel(numVaga, tamanho)) {
            ReservaVeiculo novaReserva = new ReservaVeiculo(placa, numVaga, horaEntrada, tamanho);
            listaReservas.add(novaReserva);
            vagas.atualizarDisponibilidade(numVaga, false);
            System.out.println("Reserva realizada com sucesso!");
        } else {
            System.out.println("Vaga não disponível ou incompatível com o tamanho do veículo.");
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

        ReservaVeiculo veiculoParaRemover = null;

        for (ReservaVeiculo reserva : listaReservas) {
            if (reserva.numVaga == numVaga) {
                veiculoParaRemover = reserva;
                break;
            }
        }

        if (veiculoParaRemover != null) {
            int tempoPermanencia = calcularTempoPermanencia(veiculoParaRemover.horaEntrada);

            if (tempoPermanencia > 0) {
                double valorTotal = calcularValor(tempoPermanencia);

                listaReservas.remove(veiculoParaRemover);
                vagas.atualizarDisponibilidade(numVaga, true);

                System.out.println("Veículo de placa " + veiculoParaRemover.placa + " retirado com sucesso!");
                System.out.println("Tempo de permanência: " + tempoPermanencia + " minutos.");
                System.out.println("Valor a pagar: R$" + valorTotal);
            } else {
                System.out.println("Erro ao calcular o tempo de permanência.");
            }
        } else {
            System.out.println("Veículo não encontrado na vaga informada.");
        }
    }


    private int calcularTempoPermanencia(String horaEntrada) {
        System.out.print("Digite a hora de saída do veículo (formato HH:mm): ");
        String horaSaida = sc.next();

        while (!horaSaida.matches("\\d{2}:\\d{2}")) {
            System.out.print("Formato inválido. Digite a hora de saída no formato HH:mm: ");
            horaSaida = sc.next();
        }

        String[] entrada = horaEntrada.split(":");
        int minutosEntrada = Integer.parseInt(entrada[0]) * 60 + Integer.parseInt(entrada[1]);

        String[] saida = horaSaida.split(":");
        int minutosSaida = Integer.parseInt(saida[0]) * 60 + Integer.parseInt(saida[1]);

        return minutosSaida - minutosEntrada;
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


    public void listarReservas() {
        System.out.println("Histórico de Reservas:");
        for (ReservaVeiculo reserva : listaReservas) {
            System.out.println(reserva);
        }
    }

    public class ReservaVeiculo {
        private String placa;
        private int numVaga;
        private String horaEntrada;
        private String tamanho;

        public ReservaVeiculo(String placa, int numVaga, String horaEntrada, String tamanho) {
            this.placa = placa;
            this.numVaga = numVaga;
            this.horaEntrada = horaEntrada;
            this.tamanho = tamanho;
        }

        @Override
        public String toString() {
            return "Reserva [Placa = " + placa +
                    ", Vaga = " + numVaga +
                    ", Hora de Entrada = " + horaEntrada +
                    ", Tamanho = " + tamanho + "]";
        }
    }
}
