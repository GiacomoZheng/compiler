use std::collections::HashMap;
use std::fs::File;
use std::io::prelude::*;
use serde_derive::Deserialize;

#[derive(Deserialize)]
enum ClassLevel {
    Instance = 0,
    Class = 1
}

// use toml;
#[derive(Deserialize)]
struct Entry {
    name : String,
    class_level : ClassLevel,
    fields : HashMap<String, String>
}

// impl Entry {
//     pub fn to_struct(self) -> 
// }

#[macro_export]
macro_rules! class {
    (
        $name:ident {
             $( $field_name:ident: $field_type:ty,)*
        }
    )
 => {
        struct $name {
            $($field_name: $field_type,)*
        }

        impl $name {

            fn log(self) {
                $( println!("{} -> {:?}", stringify!($field_name), self.$field_name); )*
            }
        }
    }
}

#[test]
fn test() -> std::io::Result<()> {
    let mut s = String::new();
    File::open("./res/topological_space.gm")?.read_to_string(&mut s)?;
    let entry : Entry = toml::from_str(&s).unwrap();

    Ok(())
}
