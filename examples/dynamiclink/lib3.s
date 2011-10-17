	.file	"lib3.cpp"
	.version	"01.01"
gcc2_compiled.:
.globl __throw
.section	.rodata
	.align 32
.LC0:
	.string	"\t this is lib3's func1 with no arguments !!"
.text
	.align 4
.globl func1__Fv
	.type	 func1__Fv,@function
func1__Fv:
.LFB1:
	pushl %ebp
.LCFI0:
	movl %esp,%ebp
.LCFI1:
	subl $8,%esp
.LCFI2:
	addl $-8,%esp
	pushl $endl__FR7ostream
	addl $-8,%esp
	pushl $.LC0
	pushl $cout
.LCFI3:
	call __ls__7ostreamPCc
	addl $16,%esp
	movl %eax,%eax
	pushl %eax
	call __ls__7ostreamPFR7ostream_R7ostream
	addl $16,%esp
.L263:
	movl %ebp,%esp
	popl %ebp
	ret
.LFE1:
.Lfe1:
	.size	 func1__Fv,.Lfe1-func1__Fv
.section	.rodata
	.align 32
.LC1:
	.string	"\t this is lib3's func1 with an integer !!"
.text
	.align 4
.globl func1__Fi
	.type	 func1__Fi,@function
func1__Fi:
.LFB2:
	pushl %ebp
.LCFI4:
	movl %esp,%ebp
.LCFI5:
	subl $8,%esp
.LCFI6:
	addl $-8,%esp
	pushl $endl__FR7ostream
	addl $-8,%esp
	pushl $.LC1
	pushl $cout
.LCFI7:
	call __ls__7ostreamPCc
	addl $16,%esp
	movl %eax,%eax
	pushl %eax
	call __ls__7ostreamPFR7ostream_R7ostream
	addl $16,%esp
.L264:
	movl %ebp,%esp
	popl %ebp
	ret
.LFE2:
.Lfe2:
	.size	 func1__Fi,.Lfe2-func1__Fi
.section	.rodata
	.align 32
.LC2:
	.string	"\t this is lib3's func2 with no arguments !!"
.text
	.align 4
.globl func2__Fv
	.type	 func2__Fv,@function
func2__Fv:
.LFB3:
	pushl %ebp
.LCFI8:
	movl %esp,%ebp
.LCFI9:
	subl $8,%esp
.LCFI10:
	addl $-8,%esp
	pushl $endl__FR7ostream
	addl $-8,%esp
	pushl $.LC2
	pushl $cout
.LCFI11:
	call __ls__7ostreamPCc
	addl $16,%esp
	movl %eax,%eax
	pushl %eax
	call __ls__7ostreamPFR7ostream_R7ostream
	addl $16,%esp
.L265:
	movl %ebp,%esp
	popl %ebp
	ret
.LFE3:
.Lfe3:
	.size	 func2__Fv,.Lfe3-func2__Fv
.section	.rodata
	.align 32
.LC3:
	.string	"\t this is lib3's func2 with an integer and a double !!"
.text
	.align 4
.globl func2__Fid
	.type	 func2__Fid,@function
func2__Fid:
.LFB4:
	pushl %ebp
.LCFI12:
	movl %esp,%ebp
.LCFI13:
	subl $8,%esp
.LCFI14:
	addl $-8,%esp
	pushl $endl__FR7ostream
	addl $-8,%esp
	pushl $.LC3
	pushl $cout
.LCFI15:
	call __ls__7ostreamPCc
	addl $16,%esp
	movl %eax,%eax
	pushl %eax
	call __ls__7ostreamPFR7ostream_R7ostream
	addl $16,%esp
.L266:
	movl %ebp,%esp
	popl %ebp
	ret
.LFE4:
.Lfe4:
	.size	 func2__Fid,.Lfe4-func2__Fid

.section	.eh_frame,"aw",@progbits
__FRAME_BEGIN__:
	.4byte	.LLCIE1
.LSCIE1:
	.4byte	0x0
	.byte	0x1
	.byte	0x0
	.byte	0x1
	.byte	0x7c
	.byte	0x8
	.byte	0xc
	.byte	0x4
	.byte	0x4
	.byte	0x88
	.byte	0x1
	.align 4
.LECIE1:
	.set	.LLCIE1,.LECIE1-.LSCIE1
	.4byte	.LLFDE1
.LSFDE1:
	.4byte	.LSFDE1-__FRAME_BEGIN__
	.4byte	.LFB1
	.4byte	.LFE1-.LFB1
	.byte	0x4
	.4byte	.LCFI0-.LFB1
	.byte	0xe
	.byte	0x8
	.byte	0x85
	.byte	0x2
	.byte	0x4
	.4byte	.LCFI1-.LCFI0
	.byte	0xd
	.byte	0x5
	.byte	0x4
	.4byte	.LCFI3-.LCFI1
	.byte	0x2e
	.byte	0x10
	.align 4
.LEFDE1:
	.set	.LLFDE1,.LEFDE1-.LSFDE1
	.4byte	.LLFDE3
.LSFDE3:
	.4byte	.LSFDE3-__FRAME_BEGIN__
	.4byte	.LFB2
	.4byte	.LFE2-.LFB2
	.byte	0x4
	.4byte	.LCFI4-.LFB2
	.byte	0xe
	.byte	0x8
	.byte	0x85
	.byte	0x2
	.byte	0x4
	.4byte	.LCFI5-.LCFI4
	.byte	0xd
	.byte	0x5
	.byte	0x4
	.4byte	.LCFI7-.LCFI5
	.byte	0x2e
	.byte	0x10
	.align 4
.LEFDE3:
	.set	.LLFDE3,.LEFDE3-.LSFDE3
	.4byte	.LLFDE5
.LSFDE5:
	.4byte	.LSFDE5-__FRAME_BEGIN__
	.4byte	.LFB3
	.4byte	.LFE3-.LFB3
	.byte	0x4
	.4byte	.LCFI8-.LFB3
	.byte	0xe
	.byte	0x8
	.byte	0x85
	.byte	0x2
	.byte	0x4
	.4byte	.LCFI9-.LCFI8
	.byte	0xd
	.byte	0x5
	.byte	0x4
	.4byte	.LCFI11-.LCFI9
	.byte	0x2e
	.byte	0x10
	.align 4
.LEFDE5:
	.set	.LLFDE5,.LEFDE5-.LSFDE5
	.4byte	.LLFDE7
.LSFDE7:
	.4byte	.LSFDE7-__FRAME_BEGIN__
	.4byte	.LFB4
	.4byte	.LFE4-.LFB4
	.byte	0x4
	.4byte	.LCFI12-.LFB4
	.byte	0xe
	.byte	0x8
	.byte	0x85
	.byte	0x2
	.byte	0x4
	.4byte	.LCFI13-.LCFI12
	.byte	0xd
	.byte	0x5
	.byte	0x4
	.4byte	.LCFI15-.LCFI13
	.byte	0x2e
	.byte	0x10
	.align 4
.LEFDE7:
	.set	.LLFDE7,.LEFDE7-.LSFDE7
	.ident	"GCC: (GNU) 2.95.3 20010315 (release)"
