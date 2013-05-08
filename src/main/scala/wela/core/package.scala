package wela

import weka.core.{ FastVector => WekaFastVector, Attribute => WekaAttribute, Instance => WekaInstance }
package object core {

  implicit def fastVectorToWela[E](fv: WekaFastVector): FastVector[E] = new FastVector(fv)
  implicit def welaToFastVector[E](fv: FastVector[E]): WekaFastVector = fv.wrapped

  implicit def welaAttrToWeka(attr: Attribute): WekaAttribute = attr.toWekaAttribute

  type AttributeSet = Map[Symbol, Attribute]
  implicit def attrSet(attrs: Seq[Attribute]): AttributeSet = {
    val m = attrs.map { a =>
      a.name -> a
    }
    m.toMap
  }

  type Instance = Map[Symbol, AttributeValue]
  object Instance {
    def apply(vals: (Symbol, AttributeValue)*): Instance = Map(vals: _*)
  }

  implicit def avToVal(av: AttributeValue): av.T = av.value
  implicit def strToAV(string: String) = StringValue(string)
  implicit def strToAV(string: Symbol) = SymbolValue(string)
  implicit def dblToAV(double: Double) = NumericValue(double)

  implicit def conformNominal = new ConformType[SymbolValue, NominalAttribute] {}
  implicit def conformString = new ConformType[StringValue, StringAttribute] {}
  implicit def conformNumeric[T <: NumericAttr] = new ConformType[NumericValue, T] {}

}


