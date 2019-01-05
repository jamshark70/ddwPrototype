
+ Function {
	// environment safety: asynchronous functions don't remember
	// the environment - scheduled funcs, OSCresponders, etc.
	// this is an easy way to make an environment-safe function
	// BUT... I used to have a shortcut 'e':
	// Canonical way: inEnvir { ... }
	// Shortcut way: e { ... }
	// But I got burned by this a lot of times with students,
	// who didn't have this extension. So I'm deprecating it.
	e {
		DeprecatedError(this, thisMethod, this.class.findMethod('inEnvir'), this.class).reportError;
		^this.inEnvir  // don't halt, and I want to be in control of the return
	}
}

	// in Proto, parent should contain all functions; all data should go in main dict
	// some data maybe should go in parent: provide a collection of keys
+ IdentityDictionary {
	moveFunctionsToParent { |keysToMove|
		var	movedKeys;	// because you shouldn't delete things from a collection under iteration
		movedKeys = IdentitySet.new;
		parent.isNil.if({ parent = this.class.new });
			// kvDo iterates only over main dict, not parent
		this.keysValuesDo({ |key, val|
			(val.isFunction or: { keysToMove.notNil and: { keysToMove.includes(key) } }).if({
				movedKeys.add(key);
				parent.put(key, val);
			});
		});
		movedKeys.do({ |key|
			this.removeAt(key);
		});
	}
}

// for importing methods

+ Dictionary {
	asProtoImportable {}
}

+ Ref {
	asProtoImportable { ^this.value.asProtoImportable }
}
