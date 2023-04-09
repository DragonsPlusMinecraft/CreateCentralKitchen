var ASMAPI = Java.type('net.minecraftforge.coremod.api.ASMAPI');
var Opcodes = Java.type('org.objectweb.asm.Opcodes');
var InsnList = Java.type('org.objectweb.asm.tree.InsnList');
var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');
var LabelNode = Java.type('org.objectweb.asm.tree.LabelNode');
var JumpInsnNode = Java.type('org.objectweb.asm.tree.JumpInsnNode');
var TypeInsnNode = Java.type('org.objectweb.asm.tree.TypeInsnNode');
var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
var FieldInsnNode = Java.type('org.objectweb.asm.tree.FieldInsnNode');
var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');

var BlockState = 'net/minecraft/world/level/block/state/BlockState';
var BlazeBurnerBlock = 'com/simibubi/create/content/contraptions/processing/burner/BlazeBurnerBlock';
var BlazeBurnerBlock$HeatLevel = 'com/simibubi/create/content/contraptions/processing/burner/BlazeBurnerBlock$HeatLevel';

function BlockState_hasProperty() {
    return new MethodInsnNode(
        Opcodes.INVOKEVIRTUAL,
        BlockState,
        ASMAPI.mapMethod('m_61138_'),
        '(Lnet/minecraft/world/level/block/state/properties/Property;)Z',
        false);
}

function BlockState_getValue() {
    return new MethodInsnNode(
        Opcodes.INVOKEVIRTUAL,
        BlockState,
        ASMAPI.mapMethod('m_61143_'),
        '(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;',
        false);
}

function BlazeBurnerBlock_HEAT_LEVEL() {
    return new FieldInsnNode(
        Opcodes.GETSTATIC,
        BlazeBurnerBlock,
        'HEAT_LEVEL',
        'Lnet/minecraft/world/level/block/state/properties/EnumProperty;');
}

function BlazeBurnerBlock$HeatLevel_SMOULDERING() {
    return new FieldInsnNode(
        Opcodes.GETSTATIC,
        BlazeBurnerBlock$HeatLevel,
        'SMOULDERING',
        'Lcom/simibubi/create/content/contraptions/processing/burner/BlazeBurnerBlock$HeatLevel;');
}

function BlazeBurnerBlock$HeatLevel_isAtLeast() {
    return new MethodInsnNode(
        Opcodes.INVOKEVIRTUAL,
        BlazeBurnerBlock$HeatLevel,
        'isAtLeast',
        '(Lcom/simibubi/create/content/contraptions/processing/burner/BlazeBurnerBlock$HeatLevel;)Z',
        false);
}

function findTargetForBlazeBurnerIntegration(node) {
    var iterator = node.instructions.iterator();
    var targets = [];
    while (iterator.hasNext()) {
        var aload = iterator.next();
        if (iterator.hasNext() &&
            aload.getOpcode() === Opcodes.ALOAD &&
            aload instanceof VarInsnNode)
        {
            var getstatic = iterator.next();
            if (iterator.hasNext() &&
                getstatic.getOpcode() === Opcodes.GETSTATIC &&
                getstatic instanceof FieldInsnNode &&
                getstatic.owner === 'net/minecraft/world/level/block/state/properties/BlockStateProperties' &&
                getstatic.name === ASMAPI.mapField('f_61443_'))
            {
                var invoke = iterator.next();
                if (invoke.getOpcode() === Opcodes.INVOKEVIRTUAL &&
                    invoke.owner === 'net/minecraft/world/level/block/state/BlockState' &&
                    invoke.name === ASMAPI.mapMethod('m_61138_'))
                {
                    targets.push(aload);
                }
            }
        }
    }
    return targets;
}

function initializeCoreMod() {
    return {
        'HeatableBlockEntityMixin#cck$checkBlazeBurner': {
            'target': {
                'type': 'METHOD',
                'class': 'vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity',
                'methodName': 'isHeated',
                'methodDesc': '(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z'
            },
            'transformer': function (node) {
                var targets = findTargetForBlazeBurnerIntegration(node);
                ASMAPI.log('DEBUG', 'Found ' + targets.length + ' targets in' +
                    ' vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity#isHeated' +
                    ' for coremod HeatableBlockEntityMixin#cck$checkBlazeBurner,' +
                    ' attempting injection...');
                for (var i = 0; i < targets.length; ++i) {
                    var insnList = new InsnList();
                    var target = targets[i];
                    var varIndex = target.var;
                    insnList.add(new VarInsnNode(Opcodes.ALOAD, varIndex));
                    insnList.add(BlazeBurnerBlock_HEAT_LEVEL());
                    insnList.add(BlockState_hasProperty());
                    var label = new LabelNode();
                    insnList.add(new JumpInsnNode(Opcodes.IFEQ, label));
                    insnList.add(new VarInsnNode(Opcodes.ALOAD, varIndex));
                    insnList.add(BlazeBurnerBlock_HEAT_LEVEL());
                    insnList.add(BlockState_getValue());
                    insnList.add(new TypeInsnNode(Opcodes.CHECKCAST, BlazeBurnerBlock$HeatLevel));
                    insnList.add(BlazeBurnerBlock$HeatLevel_SMOULDERING());
                    insnList.add(BlazeBurnerBlock$HeatLevel_isAtLeast());
                    insnList.add(new InsnNode(Opcodes.IRETURN));
                    insnList.add(label);
                    node.instructions.insertBefore(target, insnList);
                }
                return node;
            }
        }
    }
}