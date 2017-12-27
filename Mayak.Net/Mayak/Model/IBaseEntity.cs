namespace Mayak.Model
{
    public interface IBaseEntity<TKey>
    {
        TKey Id { get; set; }
    }
}