/**
 * 
 @author PragatiPrakashSrivastava
 */

package agent;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodTransformVisitor extends MethodVisitor implements Opcodes {

	protected int lastVisitedLine;
	protected String className;
	
    public MethodTransformVisitor(final MethodVisitor mv, String className) {
        super(ASM5, mv);
        this.className=className;
    }
    
	@Override
	public void visitLineNumber(int line, Label start) {
		if (0 != line) {
	    	lastVisitedLine = line;
	    	
			mv.visitLdcInsn(className);
			mv.visitLdcInsn(new Integer(line));
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
			mv.visitMethodInsn(INVOKESTATIC, "agent/CoverageCollector", "addMethodLine", "(Ljava/lang/String;Ljava/lang/Integer;)V", false);

	        //mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
	    	//mv.visitLdcInsn(className + " : " + line);
   	        //mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
	}
		
    	super.visitLineNumber(line, start);
	}
	
    // label visiting after branching statement
	@Override
	public void visitLabel(Label label) {
		if (0 != lastVisitedLine) {
			mv.visitLdcInsn(className);
			mv.visitLdcInsn(new Integer(lastVisitedLine));
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
			mv.visitMethodInsn(INVOKESTATIC, "agent/CoverageCollector", "addMethodLine", "(Ljava/lang/String;Ljava/lang/Integer;)V", false);

	   //mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
    	//mv.visitLdcInsn("line "+ lastVisitedLine +" executed from label: " + label.toString());
    	//mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
		}

    	super.visitLabel(label);
	}
}