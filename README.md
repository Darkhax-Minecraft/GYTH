GYTH - Get Ya' Tanks Here
====
Adds a standard tank which can be upgraded with resources to increase the tier. Tanks act as expected, and keep contents when broken.

#Default Tank Tiers
| Name                 | Identifier                 | Tier | Storage |
|----------------------|----------------------------|------|---------|
| Dark Oak Wood Planks | gyth:dark_oak              | 1    | 4B      |
| Oak Wood Planks      | gyth:oak                   | 1    | 4B      |
| Spruce Wood Planks   | gyth:spruce                | 1    | 4B      |
| Birch Wood Planks    | gyth:birch                 | 1    | 4B      |
| Acacia Wood Planks   | gyth:acacia                | 1    | 4B      |
| Jungle Wood Planks   | gyth:jungle                | 1    | 4B      |
| Diorite              | gyth:stone_diorite         | 2    | 16B     |
| Cobblestone          | gyth:stone_cobble          | 2    | 16B     |
| Red Sandstone        | gyth:sandstone_brick_red   | 2    | 16B     |
| Andesite             | gyth:stone_andesite        | 2    | 16B     |
| Sandstone            | gyth:sandstone_brick       | 2    | 16B     |
| Polished Diorite     | gyth:stone_diorite_smooth  | 2    | 16B     |
| Polished Andesite    | gyth:stone_andesite_smooth | 2    | 16B     |
| Prismarine           | gyth:prismarine            | 2    | 16B     |
| Purpur Block         | gyth:brick_purpur          | 2    | 16B     |
| Bricks               | gyth:brick                 | 2    | 16B     |
| Stone                | gyth:stone_smooth          | 2    | 16B     |
| Nether Brick         | gyth:brick_nether          | 2    | 16B     |
| Stone Bricks         | gyth:brick_stone           | 2    | 16B     |
| Granite              | gyth:stone_granite         | 2    | 16B     |
| Polished Granite     | gyth:stone_granite_smooth  | 2    | 16B     |
| End Stone Bricks     | gyth:brick_end             | 2    | 16B     |
| Obsidian             | gyth:obsidian              | 3    | 64B     |
| Block of Gold        | gyth:gold                  | 3    | 64B     |
| Block of Iron        | gyth:iron                  | 3    | 64B     |
| Lapis Lazuli Block   | gyth:lapis                 | 4    | 256B    |
| Block of Redstone    | gyth:redstone              | 4    | 256B    |
| Block of Quartz      | gyth:quartz                | 4    | 256B    |
| Block of Emerald     | gyth:emerald               | 5    | 1024B   |
| Block of Diamond     | gyth:diamond               | 5    | 1024B   |

#API Info
Gyth provides an API which allows other mods to add/remove tank tiers. This API uses Forge's IMC system, allowing other mods to add support without any sort of dependency on Gyth. There are several mods which allow configs and scripts to interact with the IMC API as well. (Those will be listed here later?) If you plan to interact with the Gyth API, I would highly recommend using the `GythWrapper` which can be found in `src/main/java/net/darkhax/gyth/api/`. Feel free to copy, modify and distribute that class as needed. 

**NOTICE: All API calls must be made before Gyth enters the init phase!**

**How to remove a tier?**
This can be done by sending a `removeTier` message to `gyth`. The message must be a `ResourceLocation` message, where the location matches a tier identifier. The list of all identifiers for default tiers can be found above. 

**How to add a tier?**
This can be done by sending an `addTier` message to `gyth`. The message must be a `NBTTagCompound` message. There are several properties which must be set to the attached tag.
- `tierName` - The name of the tier as a string. This must be an all lower case string with no spaces. This is used to create the identifier. Note that the modID is added automatically, similarly to the way forge adds it. 
- `blockId` - The identifier for the block to use as the case material, as a string. This is used for pulling texture info for the case.
- `meta` - The meta value of the case block. This must be an integer from 0 to 15. 
- `tier` - The position of the tier on the tier hierarchy. Must be an integer greater than 0 but less than 11.
- `recipe` - The item/block to use for crafting the tier. This must be a string, however there are two ways this can be used. The first is to use any ore dictionary name, the second is to use a generic itemstack format which follows a identifier#meta pattern. The identifier is a string based item/block id which follows mc's format, and the meta is any integer. Blocks require a meta of 0-15. 