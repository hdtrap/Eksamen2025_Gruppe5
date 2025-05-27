package org.example.eksamen2025_gruppe5.model;

public enum StatusOfCar {
    AvailableToLease,
    Leased,
    GettingRepaired,
    Sold,
    PendingEvaluation;

    public String getDanishLabel() {
        return switch (this) {
            case AvailableToLease -> "Tilgængelig";
            case Leased -> "Aktiv lease";
            case GettingRepaired -> "Reparation";
            case Sold -> "Solgt";
            case PendingEvaluation -> "Afventer tilsyn";
        };
    }
}

