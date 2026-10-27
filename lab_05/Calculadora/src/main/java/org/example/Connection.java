package org.example;

import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;

import java.io.InputStream;

public class Connection {
    private final Prolog engine = new Prolog();

    public Connection() throws Exception {
        try (InputStream in = getClass().getResourceAsStream("/calculo.pl")) {
            if (in == null) throw new IllegalStateException("No se encontró calculo.pl");
            engine.setTheory(new Theory(in));
        }
    }

    public String calcular(String operacion, double numeroA, double numeroB) throws Exception {
        String consulta = operacion + "(" + numeroA + ", " + numeroB + ", R).";
        SolveInfo info = engine.solve(consulta);
        return info.isSuccess() ? info.getTerm("R").toString()
                : "No se puede realizar esa operación";
    }
}
