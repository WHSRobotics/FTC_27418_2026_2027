// Written by: Christopher Gholmieh
// Package:
package org.whitneyrobotics.ftc.teamcode.Libraries.Flux;

// Imports:
import java.util.HashMap;
import java.util.Map;


// Machine:
public class FluxMachine<S extends Enum<S>> {
    // Variables (Assignment):
    // States:
    private final Map<S, FluxState<S>> states = new HashMap<>();

    // Initialized:
    private boolean initialized = false;

    // Initial:
    private S initial_state;

    // State:
    private S current_state_id;

    // Methods:
    public void add(S id, FluxState<S> state) {
        states.put(id, state);
    }

    public void set_initial(S id) {
        // Initial:
        initial_state = id;

        // Current:
        current_state_id = id;
    }

    public void update() {
        // Validation:
        if (current_state_id == null){
            return;
        }

        // Variables (Assignment):
        // State:
        FluxState<S> state = states.get(current_state_id);

        if (state == null) {
            return;
        }

        // Initialization:
        if (!initialized) {
            // Logic:
            state.enter();

            // Flag:
            initialized = true;
        }

        // Update:
        state.update();

        // Logic:
        if (state.is_finished()) {
            // Exit:
            state.exit();

            // Next:
            S next = state.next_state;

            // Logic:
            if (next == null) {
                next = initial_state;
            }

            // State:
            current_state_id = next;

            // Flag:
            initialized = false;
        }
    }

    public S get_current_state_id() {
        return current_state_id;
    }
}