// Written by: Christopher Gholmieh
// Package:
package org.whitneyrobotics.ftc.teamcode.Libraries.Flux;

// Imports:
import java.util.function.BooleanSupplier;

// Builder:
public class FluxMachineBuilder<S extends Enum<S>> {
    // Variables (Assignment):
    // Machine:
    private final FluxMachine<S> machine = new FluxMachine<>();

    // Variables (Declaration):
    // Current:
    private FluxState<S> current;

    // ID:
    private S current_id;

    // Methods:
    public static <S extends Enum<S>> FluxMachineBuilder<S> begin() {
        return new FluxMachineBuilder<>();
    }

    public FluxMachineBuilder<S> state(S id) {
        if (current != null && current.next_state == null) {
            current.next_state = id;
        }

        // Variables (Assignment):
        // Current:
        current = new FluxState<>();

        // ID:
        current_id = id;

        // Machine:
        machine.add(id, current);

        // Logic:
        return this;
    }

    public FluxMachineBuilder<S> on_enter(Runnable runnable) {
        // Enter:
        current.on_enter = runnable;

        // Logic:
        return this;
    }

    public FluxMachineBuilder<S> on_exit(Runnable runnable) {
        // Exit:
        current.on_exit = runnable;

        // Logic:
        return this;
    }

    public FluxMachineBuilder<S> on_update(Runnable runnable) {
        // Update:
        current.on_update = runnable;

        // Logic:
        return this;
    }

    public FluxMachineBuilder<S> until(BooleanSupplier conditional) {
        // Conditional:
        current.conditional = conditional;

        // Logic:
        return this;
    }

    public FluxMachineBuilder<S> wait_seconds(double seconds) {
        // Conditional:
        current.conditional = () -> current.time() >= seconds;

        // Logic:
        return this;
    }

    public FluxMachineBuilder<S> next(S id) {
        // Next:
        current.next_state = id;

        // Logic:
        return this;
    }

    public FluxMachine<S> initial(S id) {
        // Initial:
        machine.set_initial(id);

        // Logic:
        return machine;
    }

    public S get_current_state_id() {
        return current_id;
    }
}